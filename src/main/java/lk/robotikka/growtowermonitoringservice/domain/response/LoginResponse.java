package lk.robotikka.growtowermonitoringservice.domain.response;

import lk.robotikka.growtowermonitoringservice.domain.DeviceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class LoginResponse {
    private ArrayList<DeviceData> growTowerId;
}
