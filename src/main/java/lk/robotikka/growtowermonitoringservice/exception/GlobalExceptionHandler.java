package lk.robotikka.growtowermonitoringservice.exception;

import com.epic.ais.poolcontrolservice.domain.FieldValidationError;
import com.epic.ais.poolcontrolservice.util.ResponseCode;
import com.epic.ais.poolcontrolservice.util.ResponseGenerator;
import com.epic.ais.poolcontrolservice.util.ResponseMessage;
import com.epic.ais.poolcontrolservice.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale lang) {
        List<FieldValidationError> fieldValidationErrorList = new ArrayList<>();

        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        if (objectError instanceof FieldError) {
            ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = messageSource.getMessage(fieldError.getDefaultMessage(), new Object[]{}, lang);
                fieldValidationErrorList.add(new FieldValidationError(fieldName, errorMessage));
            });
        }

        logger.error("Field error - {} ", Utility.objectToJson(fieldValidationErrorList));

        return responseGenerator.generateResponse(fieldValidationErrorList, ResponseCode.METHOD_ARGUMENT_NOTVALID, ResponseMessage.REQUIRED_DATA_ELEMENT_MISSING_MSG, new Object[]{}, lang);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.RESOURCE_NOT_FOUND, ResponseMessage.RESOURCE_NOT_FOUND_MSG, new Object[]{}, lang);
    }

    @ExceptionHandler(ObjectNotFondException.class)
    public ResponseEntity<?> handleObjectNotFondException(ObjectNotFondException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.ENTITY_NOT_FOUND, e.getMessage(), new Object[]{}, lang);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleCommonException(CommonException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(e.getCode(), e.getMessage(), new Object[]{}, lang);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.MESSAGE_NOTREADABLE, ResponseMessage.MESSAGE_NOTREADABLE_MSG, new Object[]{}, lang);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.ILLEGAL_ARGUMENT, ResponseMessage.ILLEGAL_ARGUMENT_MSG, new Object[]{}, lang);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.NULL_POINTER, ResponseMessage.NULL_POINTER_MSG, new Object[]{}, lang);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handleConnectException(ConnectException e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.CONNECT_EXCEPTION, ResponseMessage.CONNECT_EXCEPTION_MSG, new Object[]{}, lang);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e, Locale lang) {
        logger.error(e.toString());
        return responseGenerator.generateResponse(ResponseCode.FAILED, ResponseMessage.SYSTEM_ERROR_MSG, new Object[]{}, lang);
    }

}
