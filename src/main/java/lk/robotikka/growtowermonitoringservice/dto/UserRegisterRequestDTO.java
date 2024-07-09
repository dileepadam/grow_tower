package lk.robotikka.growtowermonitoringservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.robotikka.growtowermonitoringservice.validator.group.FirstValidation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterRequestDTO {
    @NotNull(message = "val.mobileno.required", groups = FirstValidation.class)
    private Integer mobileNo;
    @NotBlank(message = "val.password.required", groups = FirstValidation.class)
    private String password;
}
