package lk.robotikka.growtowermonitoringservice.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class SendDataPushRequest {
    private String token;
    private Map<String, String> data;
}
