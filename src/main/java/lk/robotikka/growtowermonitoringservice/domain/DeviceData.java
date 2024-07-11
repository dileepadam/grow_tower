package lk.robotikka.growtowermonitoringservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceData {
    private String serialNo;
    private int deviceId;
}
