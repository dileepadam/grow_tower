package lk.robotikka.growtowermonitoringservice.service;

public interface MqttService {
    void getGrowTowerMetrics(String growTowerMetrics) throws Exception;
}
