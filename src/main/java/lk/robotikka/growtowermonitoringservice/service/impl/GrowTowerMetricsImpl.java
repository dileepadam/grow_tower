package lk.robotikka.growtowermonitoringservice.service.impl;

import lk.robotikka.growtowermonitoringservice.domain.response.GrowTowerMetricsResponse;
import lk.robotikka.growtowermonitoringservice.domain.request.GetGrowTowerDataRequest;
import lk.robotikka.growtowermonitoringservice.entity.*;
import lk.robotikka.growtowermonitoringservice.repository.*;
import lk.robotikka.growtowermonitoringservice.service.GrowTowerMetricsService;
import lk.robotikka.growtowermonitoringservice.util.ResponseCode;
import lk.robotikka.growtowermonitoringservice.util.ResponseGenerator;
import lk.robotikka.growtowermonitoringservice.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
public class GrowTowerMetricsImpl implements GrowTowerMetricsService {

    private static final Logger logger = LoggerFactory.getLogger(GrowTowerMetricsService.class);

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private GrowTowerDataRepository growTowerDataRepository;

    @Autowired
    private MobileDeviceRepository mobileDeviceRepository;

    @Autowired
    private DeviceRegisterRepository deviceRegisterRepository;

    @Autowired
    private GrowTowerMetricsRepository growTowerMetricsRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> getDeviceData(GetGrowTowerDataRequest getGrowTowerDataRequest, Locale locale) {

        Optional<UserData> userDataOpt = userDataRepository.findByMobileNo(getGrowTowerDataRequest.getMobileNo());
        Optional<GrowTowerData> growTowerDataOpt = growTowerDataRepository.findBySerialNo(getGrowTowerDataRequest.getSerialNo());
        Optional<MobileDevice> mobileDeviceOpt = mobileDeviceRepository.findByUuid(getGrowTowerDataRequest.getUuid());

        if (userDataOpt.isPresent() && growTowerDataOpt.isPresent() && mobileDeviceOpt.isPresent()) {
            UserData userData = userDataOpt.get();
            GrowTowerData growTowerData = growTowerDataOpt.get();
            MobileDevice mobileDevice = mobileDeviceOpt.get();

            Optional<DeviceRegister> deviceRegisterOpt = Optional.ofNullable(deviceRegisterRepository.findByUserAndDeviceAndMobileDevice(userData, growTowerData, mobileDevice));

            if (deviceRegisterOpt.isPresent()) {
                Optional<GrowTowerMetrics> growTowerMetricsOpt = growTowerMetricsRepository.findByDevice(growTowerData);

                if (growTowerMetricsOpt.isPresent()) {
                    GrowTowerMetrics growTowerMetrics = growTowerMetricsOpt.get();
                    GrowTowerMetricsResponse response = createGrowTowerMetricsResponse(growTowerMetrics);

                    logger.info("Grow Tower metrics found");
                    return responseGenerator.generateResponse(response, ResponseCode.SUCCESS, ResponseMessage.NO_METRIC_FOUND, new Object[]{}, locale);
                } else {
                    logger.info("Grow Tower metrics not found");
                    return responseGenerator.generateResponse(ResponseCode.SUCCESS, ResponseMessage.NO_METRIC_FOUND, new Object[]{}, locale);
                }
            }
        }

        logger.error("Device not registered properly");
        return responseGenerator.generateResponse(ResponseCode.GET_GROW_TOWER_DATA_FAIL, ResponseMessage.GET_GROW_TOWER_DATA_FAIL, new Object[]{}, locale);
    }

    private GrowTowerMetricsResponse createGrowTowerMetricsResponse(GrowTowerMetrics growTowerMetrics) {
        GrowTowerMetricsResponse response = new GrowTowerMetricsResponse();
        response.setWaterTemp(growTowerMetrics.getWaterTemp());
        response.setWaterLvl(growTowerMetrics.getWaterLvl());
        response.setNutritionLvl(growTowerMetrics.getNutritionLvl());
        response.setEnvironmentTemp(growTowerMetrics.getEnvironmentTemp());
        response.setEnvironmentHumidity(growTowerMetrics.getEnvironmentHumidity());
        return response;
    }
}
