package lk.robotikka.growtowermonitoringservice.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.robotikka.growtowermonitoringservice.validator.group.FirstValidation;
import lombok.Data;

@Data
public class DeviceRegisterRequest {
    private Integer mobileNo;
    private String uuid;
    private String serialNo;
    private String deviceName;
    private String deviceType;
    private Float lon;
    private Float lat;
}
