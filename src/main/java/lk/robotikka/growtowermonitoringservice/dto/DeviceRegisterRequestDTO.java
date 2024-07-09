package lk.robotikka.growtowermonitoringservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.robotikka.growtowermonitoringservice.validator.group.FirstValidation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class DeviceRegisterRequestDTO implements Serializable {
    @NotNull(message = "val.mobileno.required", groups = FirstValidation.class)
    private Integer mobileNo;
    @NotBlank(message = "val.uuid.required", groups = FirstValidation.class)
    private String uuid;
    @NotBlank(message = "val.deviceserial.required", groups = FirstValidation.class)
    private String serialNo;
    private String deviceName;
    private String deviceType;
    @NotNull(message = "val.device.location.required", groups = FirstValidation.class)
    private Float lon;
    @NotNull(message = "val.device.location.required", groups = FirstValidation.class)
    private Float lat;
}
