package lk.robotikka.growtowermonitoringservice.service.impl;

import lk.robotikka.growtowermonitoringservice.domain.request.LoginRequest;
import lk.robotikka.growtowermonitoringservice.domain.request.UserRegisterRequest;
import lk.robotikka.growtowermonitoringservice.domain.response.LoginResponse;
import lk.robotikka.growtowermonitoringservice.entity.DeviceRegister;
import lk.robotikka.growtowermonitoringservice.entity.MobileDevice;
import lk.robotikka.growtowermonitoringservice.entity.UserData;
import lk.robotikka.growtowermonitoringservice.enums.Status;
import lk.robotikka.growtowermonitoringservice.repository.DeviceRegisterRepository;
import lk.robotikka.growtowermonitoringservice.repository.MobileDeviceRepository;
import lk.robotikka.growtowermonitoringservice.repository.UserDataRepository;
import lk.robotikka.growtowermonitoringservice.service.UserService;
import lk.robotikka.growtowermonitoringservice.util.ResponseCode;
import lk.robotikka.growtowermonitoringservice.util.ResponseGenerator;
import lk.robotikka.growtowermonitoringservice.util.ResponseMessage;
import lk.robotikka.growtowermonitoringservice.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private MobileDeviceRepository mobileDeviceRepository;

    @Autowired
    private DeviceRegisterRepository deviceRegisterRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> createUser(UserRegisterRequest userRegisterRequest, Locale locale) {
        return userDataRepository.findByMobileNo(userRegisterRequest.getMobileNo())
                .map(user -> responseGenerator.generateResponse(ResponseCode.USER_ALREADY_REGISTERED, ResponseMessage.USER_ALREADY_REGISTERED, new Object[]{}, locale))
                .orElseGet(() -> {
                    UserData userData = new UserData();
                    userData.setMobileNo(userRegisterRequest.getMobileNo());
                    userData.setPassword(Utility.hashPassword(userRegisterRequest.getPassword()));
                    userData.setCreatedDate(Utility.getSystemDate());
                    userData.setStatus(String.valueOf(Status.PEND));
                    userData.setLastUpdatedDate(Utility.getSystemDate());
                    userDataRepository.save(userData);

                    logger.info("User created successfully");
                    return responseGenerator.generateResponse(ResponseCode.SUCCESS, ResponseMessage.USER_REGISTRATION_SUCCESS, new Object[]{}, locale);
                });
    }

    @Override
    @Transactional
    public ResponseEntity<Object> login(LoginRequest loginRequest, Locale locale) {
        Optional<UserData> user = userDataRepository.findByMobileNo(loginRequest.getMobileNo());

        if (user.isPresent()) {
            UserData userData = user.get();
            if (userData.getPassword().equals(Utility.hashPassword(loginRequest.getPassword()))) {
                return handleMobileDeviceAndLogin(loginRequest, locale, userData);
            } else {
                return responseGenerator.generateResponse(ResponseCode.LOGIN_FAIL, ResponseMessage.LOGIN_FAIL, new Object[]{}, locale);
            }
        }
        return responseGenerator.generateResponse(ResponseCode.LOGIN_FAIL, ResponseMessage.LOGIN_FAIL, new Object[]{}, locale);
    }

    private ResponseEntity<Object> handleMobileDeviceAndLogin(LoginRequest loginRequest, Locale locale, UserData userData) {
        return mobileDeviceRepository.findByUuid(loginRequest.getUuid())
                .map(mobileDevice -> handleExistingMobileDevice(loginRequest, locale, mobileDevice))
                .orElseGet(() -> handleNewMobileDevice(loginRequest, locale, userData));
    }

    private ResponseEntity<Object> handleNewMobileDevice(LoginRequest loginRequest, Locale locale, UserData userData) {
        MobileDevice mobileDevice = new MobileDevice();
        mobileDevice.setUuid(loginRequest.getUuid());
        mobileDevice.setPushId(loginRequest.getPushId());
        mobileDevice.setOsType(String.valueOf(loginRequest.getOsType()));
        mobileDevice.setOsVersion(loginRequest.getOsVersion());
        mobileDevice.setStatus(String.valueOf(Status.ACT));
        mobileDevice.setCreatedDate(Utility.getSystemDate());
        mobileDevice.setUpdatedDate(Utility.getSystemDate());

        MobileDevice savedMobileDevice = mobileDeviceRepository.save(mobileDevice);
        if (savedMobileDevice == null) {
            logger.error("Failed to save Mobile Device");
            throw new RuntimeException("Failed to save Mobile Device");
        }

        return generateLoginResponse(loginRequest, locale, savedMobileDevice);
    }

    private ResponseEntity<Object> handleExistingMobileDevice(LoginRequest loginRequest, Locale locale, MobileDevice mobileDevice) {
        return generateLoginResponse(loginRequest, locale, mobileDevice);
    }

    private ResponseEntity<Object> generateLoginResponse(LoginRequest loginRequest, Locale locale, MobileDevice mobileDevice) {
        List<DeviceRegister> registeredDevices = deviceRegisterRepository.findByUserMobileNo(loginRequest.getMobileNo());

        if (registeredDevices.isEmpty()) {
            return responseGenerator.generateResponse(ResponseCode.NO_DEVICE_FOUND_FOR_USER, ResponseMessage.NO_DEVICE_FOUND_FOR_USER, new Object[]{}, locale);
        }

        ArrayList<String> serialNumbers = new ArrayList<>();
        registeredDevices.forEach(deviceRegister -> {
            DeviceRegister newDeviceRegister = new DeviceRegister();
            newDeviceRegister.setUser(deviceRegister.getUser());
            newDeviceRegister.setDevice(deviceRegister.getDevice());
            newDeviceRegister.setMobileDevice(mobileDevice);
            deviceRegisterRepository.save(newDeviceRegister);

            serialNumbers.add(newDeviceRegister.getDevice().getSerialNo());
        });

        LoginResponse response = new LoginResponse(serialNumbers);
        return responseGenerator.generateResponse(response, ResponseCode.SUCCESS, ResponseMessage.LOGIN_SUCCESS, new Object[]{}, locale);
    }
}
