package lk.robotikka.growtowermonitoringservice.service.impl;

import lk.robotikka.growtowermonitoringservice.domain.request.DeviceRegisterRequest;
import lk.robotikka.growtowermonitoringservice.entity.*;
import lk.robotikka.growtowermonitoringservice.enums.Status;
import lk.robotikka.growtowermonitoringservice.repository.DeviceRegisterRepository;
import lk.robotikka.growtowermonitoringservice.repository.GrowTowerDataRepository;
import lk.robotikka.growtowermonitoringservice.repository.MobileDeviceRepository;
import lk.robotikka.growtowermonitoringservice.repository.UserDataRepository;
import lk.robotikka.growtowermonitoringservice.service.GrowTowerService;
import lk.robotikka.growtowermonitoringservice.util.ResponseCode;
import lk.robotikka.growtowermonitoringservice.util.ResponseGenerator;
import lk.robotikka.growtowermonitoringservice.util.ResponseMessage;
import lk.robotikka.growtowermonitoringservice.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class GrowTowerServiceImpl implements GrowTowerService {
    private static final Logger logger = LoggerFactory.getLogger(GrowTowerService.class);

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private GrowTowerDataRepository growTowerDataRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private DeviceRegisterRepository deviceRegisterRepository;

    @Autowired
    private MobileDeviceRepository mobileDeviceRepository;

    @Override
    public ResponseEntity<Object> registerDevice(DeviceRegisterRequest deviceRegisterRequest, Locale locale) {
        Optional<GrowTowerData> growTowerData = growTowerDataRepository.findBySerialNo(deviceRegisterRequest.getSerialNo());
        Optional<UserData> userData = userDataRepository.findByMobileNo(deviceRegisterRequest.getMobileNo());
        Optional<MobileDevice> mobileDevice = mobileDeviceRepository.findByUuid(deviceRegisterRequest.getUuid());

        if (growTowerData.isPresent() || userData.isEmpty()) {
            logger.error("Device Registration Fail: GrowTowerData already exists or UserData not found.");
            return responseGenerator.generateResponse(ResponseCode.DEVICE_REGISTRATION_FAIL, ResponseMessage.DEVICE_REGISTRATION_FAIL, new Object[]{}, locale);
        }

        if (mobileDevice.isEmpty()) {
            logger.error("Device Registration Fail: Mobile Device not found.");
            return responseGenerator.generateResponse(ResponseCode.DEVICE_REGISTRATION_FAIL, ResponseMessage.DEVICE_REGISTRATION_FAIL, new Object[]{}, locale);
        }

        GrowTowerData savedGrowTowerData = saveGrowTowerData(deviceRegisterRequest);
        saveDeviceRegister(userData.get(), savedGrowTowerData, mobileDevice.get());

        logger.info("Device Registration Successful");
        return responseGenerator.generateResponse(ResponseCode.SUCCESS, ResponseMessage.DEVICE_REGISTRATION_SUCCESS, new Object[]{}, locale);
    }

    private GrowTowerData saveGrowTowerData(DeviceRegisterRequest deviceRegisterRequest) {
        GrowTowerData data = new GrowTowerData();
        data.setSerialNo(deviceRegisterRequest.getSerialNo());
        data.setDeviceName(deviceRegisterRequest.getDeviceName());
        data.setDeviceType(deviceRegisterRequest.getDeviceType());
        data.setStatus(String.valueOf(Status.ACT));
        data.setLongitude(deviceRegisterRequest.getLon());
        data.setLatitude(deviceRegisterRequest.getLat());
        data.setCreatedDate(Utility.getSystemDate());
        data.setLastUpdateDate(Utility.getSystemDate());

        GrowTowerData savedGrowTowerData = growTowerDataRepository.save(data);
        if (savedGrowTowerData == null) {
            logger.error("Failed to save GrowTowerData");
            throw new RuntimeException("Failed to save GrowTowerData");
        }

        return savedGrowTowerData;
    }

    private void saveDeviceRegister(UserData userData, GrowTowerData growTowerData, MobileDevice mobileDevice) {
        DeviceRegisterPK deviceRegisterPK = new DeviceRegisterPK();
        deviceRegisterPK.setUserId(userData.getUserId());
        deviceRegisterPK.setDeviceId(growTowerData.getDeviceId());
        deviceRegisterPK.setMobDeviceId(mobileDevice.getMobDeviceId());

        DeviceRegister deviceRegister = new DeviceRegister();
        deviceRegister.setDeviceRegisterPK(deviceRegisterPK);
        deviceRegister.setUser(userData);
        deviceRegister.setDevice(growTowerData);
        deviceRegister.setMobileDevice(mobileDevice);
        deviceRegister.setCreatedDate(Utility.getSystemDate());

        deviceRegisterRepository.save(deviceRegister);

        logger.info("Device Registration Successful with mobile and user");
    }
}
