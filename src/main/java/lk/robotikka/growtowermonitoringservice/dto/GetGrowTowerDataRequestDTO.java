package lk.robotikka.growtowermonitoringservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.robotikka.growtowermonitoringservice.validator.group.FirstValidation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetGrowTowerDataRequestDTO {
    @NotNull(message = "val.mobileno.required", groups = FirstValidation.class)
    private int mobileNo;
    @NotBlank(message = "val.uuid.required", groups = FirstValidation.class)
    private String uuid;
    @NotBlank(message = "val.deviceserial.required", groups = FirstValidation.class)
    private String serialNo;
}
