package lk.robotikka.growtowermonitoringservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.robotikka.growtowermonitoringservice.enums.OSType;
import lk.robotikka.growtowermonitoringservice.validator.group.FirstValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
    @NotNull(message = "val.mobileno.required", groups = FirstValidation.class)
    private Integer mobileNo;
    @NotBlank(message = "val.password.required", groups = FirstValidation.class)
    private String password;
    @NotBlank(message = "val.pushid.required", groups = FirstValidation.class)
    private String pushId;
    @NotBlank(message = "val.uuid.required", groups = FirstValidation.class)
    private String uuid;
    @NotNull(message = "val.ostype.required", groups = FirstValidation.class)
    private OSType osType;
    private String osVersion;
}
