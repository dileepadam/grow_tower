package lk.robotikka.growtowermonitoringservice.domain.request;

import lombok.Data;

@Data
public class GetGrowTowerDataRequest {
    private int mobileNo;
    private String uuid;
    private String serialNo;
}
