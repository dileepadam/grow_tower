package lk.robotikka.growtowermonitoringservice.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response object for user login")
public class Response<T> {
    @Schema(description = "Status of the response", example = "00")
    private String status;
    @Schema(description = "Message accompanying the response", example = "Successful")
    private String message;
    @Schema(description = "Data payload")
    private T data;
}
