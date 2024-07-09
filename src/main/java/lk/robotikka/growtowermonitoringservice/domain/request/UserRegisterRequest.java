package lk.robotikka.growtowermonitoringservice.domain.request;

import lk.robotikka.growtowermonitoringservice.enums.OSType;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private int mobileNo;
    private String password;
}
