package lk.robotikka.growtowermonitoringservice.domain.mqtt;

import lombok.Data;

@Data
public class GrowTowerMetricsMqtt {
    private String serialNo;
    private int nutritionLvl;
    private int waterLvl;
    private int waterTemp;
    private int environmentTemp;
    private int environmentHumidity;
}
