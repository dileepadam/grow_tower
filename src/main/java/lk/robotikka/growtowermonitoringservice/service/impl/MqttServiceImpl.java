package lk.robotikka.growtowermonitoringservice.service.impl;

import lk.robotikka.growtowermonitoringservice.domain.mqtt.GrowTowerMetricsMqtt;
import lk.robotikka.growtowermonitoringservice.entity.GrowTowerData;
import lk.robotikka.growtowermonitoringservice.entity.GrowTowerMetrics;
import lk.robotikka.growtowermonitoringservice.entity.GrowTowerMetricsHistory;
import lk.robotikka.growtowermonitoringservice.entity.GrowTowerMetricsPK;
import lk.robotikka.growtowermonitoringservice.repository.GrowTowerDataRepository;
import lk.robotikka.growtowermonitoringservice.repository.GrowTowerMetricsHistoryRepository;
import lk.robotikka.growtowermonitoringservice.repository.GrowTowerMetricsRepository;
import lk.robotikka.growtowermonitoringservice.service.MqttService;
import lk.robotikka.growtowermonitoringservice.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MqttServiceImpl implements MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    private GrowTowerMetricsRepository growTowerMetricsRepository;

    @Autowired
    private GrowTowerDataRepository growTowerDataRepository;

    @Autowired
    private GrowTowerMetricsHistoryRepository growTowerMetricsHistoryRepository;

    @Override
    @Transactional
    public void getGrowTowerMetrics(String growTowerMetrics) throws Exception {
        GrowTowerMetricsMqtt metricsMqtt = Utility.jsonToObject(growTowerMetrics, GrowTowerMetricsMqtt.class);
        Optional<GrowTowerData> growTowerDataOpt = growTowerDataRepository.findBySerialNo(metricsMqtt.getSerialNo());

        if (growTowerDataOpt.isPresent()) {
            GrowTowerData growTowerData = growTowerDataOpt.get();
            Optional<GrowTowerMetrics> metricsOpt = growTowerMetricsRepository.findByDevice(growTowerData);

            if (metricsOpt.isPresent()) {
                updateGrowTowerMetrics(metricsOpt.get(), metricsMqtt);
            } else {
                addGrowTowerMetrics(growTowerData, metricsMqtt);
            }

            saveMetricsHistory(growTowerData, metricsMqtt);
            logger.info("Grow Tower Metrics processed successfully.");
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
}
