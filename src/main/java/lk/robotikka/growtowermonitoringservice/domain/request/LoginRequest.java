package lk.robotikka.growtowermonitoringservice.domain.request;

import lk.robotikka.growtowermonitoringservice.enums.OSType;
import lombok.Data;

@Data
public class LoginRequest {
    private int mobileNo;
    private String password;
    private String pushId;
    private String uuid;
    private OSType osType;
    private String osVersion;
}
