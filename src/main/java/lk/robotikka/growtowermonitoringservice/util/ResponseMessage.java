package lk.robotikka.growtowermonitoringservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMessage {


    public static final String RESOURCE_NOT_FOUND_MSG = "val.resource.not.found";

    public static final String SYSTEM_ERROR_MSG = "val.system.error";

    public static final String REQUIRED_DATA_ELEMENT_MISSING_MSG = "val.mandatory.fields.missing";

    public static final String MESSAGE_NOTREADABLE_MSG = "val.message.notreadable";

    public static final String ILLEGAL_ARGUMENT_MSG = "val.illegal.argument";

    public static final String NULL_POINTER_MSG = "val.null.pointer";

    public static final String CONNECT_EXCEPTION_MSG = "val.connect.exception";
    public static final String USER_ALREADY_REGISTERED = "val.user.registered";
    public static final String DEVICE_REGISTRATION_FAIL = "val.device.registration.fail";
    public static final String USER_REGISTRATION_SUCCESS = "val.user.registration.success";
    public static final String LOGIN_FAIL = "val.login.fail";
    public static final String LOGIN_SUCCESS = "val.login.success";
    public static final String NO_DEVICE_FOUND_FOR_USER = "val.device.no.device.found";
    public static final String DEVICE_REGISTRATION_SUCCESS = "val.device.registration.success";

    public static final String GET_GROW_TOWER_DATA_FAIL = "val.device.register.invalid";
    public static final String NO_METRIC_FOUND = "val.metrics.notfound";


}
