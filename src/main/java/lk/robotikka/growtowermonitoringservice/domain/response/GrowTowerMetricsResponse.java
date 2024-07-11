package lk.robotikka.growtowermonitoringservice.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrowTowerMetricsResponse {
    private int nutritionLvl;
    private int waterLvl;
    private int waterTemp;
    private int environmentTemp;
    private int environmentHumidity;
}
