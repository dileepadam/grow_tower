package lk.robotikka.growtowermonitoringservice.util;

import lk.robotikka.growtowermonitoringservice.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResponseGenerator {

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(ResponseGenerator.class);

    public ResponseEntity<Object> generateResponse(String responseCode, String messagePropertyName, Object[] params, Locale lang) {
        Response response = new Response<>();
        response.setStatus(responseCode);
        response.setMessage(messageSource.getMessage(messagePropertyName, params, lang));

        logger.debug(responseCode + "-" + (messageSource.getMessage(messagePropertyName, params, lang) + "\n"));
        logger.debug(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> generateResponse(Object data, String responseCode, String messagePropertyName, Object[] params, Locale lang) {
        Response response = new Response();
        response.setStatus(responseCode);
        response.setMessage(messageSource.getMessage(messagePropertyName, params, lang));
        response.setData(data);

        logger.debug(responseCode + "-" + (messageSource.getMessage(messagePropertyName, params, lang) + "\n"));
        logger.debug(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
