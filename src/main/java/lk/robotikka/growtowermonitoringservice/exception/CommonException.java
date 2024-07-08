package lk.robotikka.growtowermonitoringservice.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException {
    private String code;

    public CommonException() {
    }

    public CommonException(String code, String message) {
        super(message);
        this.code = code;
    }
}
