package lk.robotikka.growtowermonitoringservice.service.impl;

import lk.robotikka.growtowermonitoringservice.domain.mqtt.GrowTowerMetricsMqtt;
import lk.robotikka.growtowermonitoringservice.domain.request.NotificationRequest;
import lk.robotikka.growtowermonitoringservice.entity.*;
import lk.robotikka.growtowermonitoringservice.enums.AlertType;
import lk.robotikka.growtowermonitoringservice.repository.*;
import lk.robotikka.growtowermonitoringservice.service.MqttService;
import lk.robotikka.growtowermonitoringservice.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class MqttServiceImpl implements MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    private GrowTowerMetricsRepository growTowerMetricsRepository;

    @Autowired
    private GrowTowerDataRepository growTowerDataRepository;

    @Autowired
    private GrowTowerMetricsHistoryRepository growTowerMetricsHistoryRepository;

    @Autowired
    private DeviceRegisterRepository deviceRegisterRepository;

    @Autowired
    private CommonConfigRepository commonConfigRepository;

    @Autowired
    private FCMService fcmService;

    @Override
    @Transactional
    public void getGrowTowerMetrics(String growTowerMetrics) throws Exception {
        GrowTowerMetricsMqtt metricsMqtt = Utility.jsonToObject(growTowerMetrics, GrowTowerMetricsMqtt.class);
        Optional<GrowTowerData> growTowerDataOpt = growTowerDataRepository.findBySerialNo(metricsMqtt.getSerialNo());

        if (growTowerDataOpt.isPresent()) {
            GrowTowerData growTowerData = growTowerDataOpt.get();
            growTowerMetricsRepository.findByDevice(growTowerData)
                    .ifPresentOrElse(
                            metrics -> updateGrowTowerMetrics(metrics, metricsMqtt),
                            () -> addGrowTowerMetrics(growTowerData, metricsMqtt)
                    );

            saveMetricsHistory(growTowerData, metricsMqtt);
            logger.info("Grow Tower Metrics processed successfully.");
            setErrorMessage(metricsMqtt, growTowerData.getSerialNo());
        } else {
            logger.error("Unauthorized device found.");
        }
    }

    @Transactional
    protected void updateGrowTowerMetrics(GrowTowerMetrics metrics, GrowTowerMetricsMqtt metricsMqtt) {
        try {
            metrics.setWaterLvl((byte) metricsMqtt.getWaterLvl());
            metrics.setWaterTemp((byte) metricsMqtt.getWaterTemp());
            metrics.setNutritionLvl((byte) metricsMqtt.getNutritionLvl());
            metrics.setEnvironmentHumidity((byte) metricsMqtt.getEnvironmentHumidity());
            metrics.setEnvironmentTemp((byte) metricsMqtt.getEnvironmentTemp());
            metrics.setLastUpdatedTime(Utility.getSystemDate());

            growTowerMetricsRepository.save(metrics);
            logger.info("Grow Tower Metrics updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update Grow Tower Metrics.", e);
            throw new RuntimeException("Grow Tower Metrics Update Fail", e);
        }
    }

    @Transactional
    protected void addGrowTowerMetrics(GrowTowerData growTowerData, GrowTowerMetricsMqtt metricsMqtt) {
        try {
            GrowTowerMetricsPK metricsPK = new GrowTowerMetricsPK();
            metricsPK.setDeviceId(growTowerData.getDeviceId());

            GrowTowerMetrics metrics = new GrowTowerMetrics();
            metrics.setGrowTowerMetricsPK(metricsPK);
            metrics.setDevice(growTowerData);
            metrics.setWaterLvl((byte) metricsMqtt.getWaterLvl());
            metrics.setWaterTemp((byte) metricsMqtt.getWaterTemp());
            metrics.setNutritionLvl((byte) metricsMqtt.getNutritionLvl());
            metrics.setEnvironmentHumidity((byte) metricsMqtt.getEnvironmentHumidity());
            metrics.setEnvironmentTemp((byte) metricsMqtt.getEnvironmentTemp());
            metrics.setCreatedDate(Utility.getSystemDate());
            metrics.setLastUpdatedTime(Utility.getSystemDate());

            growTowerMetricsRepository.save(metrics);
            logger.info("Grow Tower Metrics added successfully.");
        } catch (Exception e) {
            logger.error("Failed to add Grow Tower Metrics.", e);
            throw new RuntimeException("Grow Tower Metrics Addition Fail", e);
        }
    }

    @Transactional
    protected void saveMetricsHistory(GrowTowerData growTowerData, GrowTowerMetricsMqtt metricsMqtt) {
        try {
            GrowTowerMetricsHistory metricsHistory = new GrowTowerMetricsHistory();
            metricsHistory.setDevice(growTowerData);
            metricsHistory.setWaterLvl((byte) metricsMqtt.getWaterLvl());
            metricsHistory.setWaterTemp((byte) metricsMqtt.getWaterTemp());
            metricsHistory.setNutritionLvl((byte) metricsMqtt.getNutritionLvl());
            metricsHistory.setEnvironmentHumidity((byte) metricsMqtt.getEnvironmentHumidity());
            metricsHistory.setEnvironmentTemp((byte) metricsMqtt.getEnvironmentTemp());
            metricsHistory.setCreatedDate(Utility.getSystemDate());

            growTowerMetricsHistoryRepository.save(metricsHistory);
            logger.info("Grow Tower Metrics History saved successfully.");
        } catch (Exception e) {
            logger.error("Failed to save Grow Tower Metrics History.", e);
            throw new RuntimeException("Grow Tower Metrics History Update Fail", e);
        }
    }

    protected void sendPushNotification(String serialNo, String body, String topic, String title) {
        List<MobileDevice> mobileDevices = deviceRegisterRepository.findAllRegisteredMobileDevicesByGrowTowerSerialNo(serialNo);

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setBody(body);
        notificationRequest.setTitle(title);
        notificationRequest.setTopic(topic);

        for (MobileDevice mobileDevice : mobileDevices) {
            notificationRequest.setToken(mobileDevice.getPushId());
            try {
                fcmService.sendMessageToToken(notificationRequest);
            } catch (InterruptedException | ExecutionException e) {
                logger.info("Push Notification Sending Failed");
                throw new RuntimeException("Push Notification Sending Failed", e);
            }
        }
    }

    protected AlertType checkIsError(GrowTowerMetricsMqtt metricsMqtt) {
        return commonConfigRepository.findById(1)
                .map(commonConfig -> {
                    if (commonConfig.getMinWaterTemp() >= metricsMqtt.getWaterTemp()) {
                        return AlertType.MIN_WATER_LVL;
                    } else if (commonConfig.getMaxWaterLvl() <= metricsMqtt.getWaterLvl()) {
                        return AlertType.MAX_WATER_LVL;
                    } else if (commonConfig.getMinWaterLvl() >= metricsMqtt.getWaterLvl()) {
                        return AlertType.MIN_TEMP;
                    } else if (commonConfig.getMaxWaterTemp() <= metricsMqtt.getWaterTemp()) {
                        return AlertType.MAX_TEMP;
                    } else {
                        return AlertType.NO_ERROR;
                    }
                })
                .orElse(AlertType.NO_ERROR);
    }

    protected void setErrorMessage(GrowTowerMetricsMqtt growTowerMetricsMqtt, String serialNo) {
        AlertType alertType = checkIsError(growTowerMetricsMqtt);
        String body;
        switch (alertType) {
            case MAX_TEMP:
                body = "Max Water Temperature Exceeded";
                break;
            case MIN_TEMP:
                body = "Min Water Temperature Exceeded";
                break;
            case MAX_WATER_LVL:
                body = "Max Water Level Exceeded";
                break;
            case MIN_WATER_LVL:
                body = "Min Water Level Exceeded";
                break;
            case NO_ERROR:
            default:
                body = "No Error Found";
                break;
        }
        sendPushNotification(serialNo, body, body, "Alert");
    }
}
