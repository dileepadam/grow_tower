package lk.robotikka.growtowermonitoringservice.domain;

import lombok.Data;

@Data
public class FieldValidationError {
    private String field;
    private String defaultMessage;

    public FieldValidationError() {

    }

    public FieldValidationError(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }
}
