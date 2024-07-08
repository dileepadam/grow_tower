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


    //System Error Messages

    public static final String EMPTY_LANGUAGE_LIST_MSG = "val.empty.language.list";

    public static final String DEVICE_REGISTRATION_SUCCESS_MSG = "val.device.registration.success";

    public static final String DEVICE_ALREADY_REGISTERED_MSG = "val.device.already.registered";

    public static final String SUCCESS_MSG = "val.request.successful";

    public static final String INVALID_REQUEST_DATA_MSG = "val.invalid.request.data";

    public static final String EMPTY_POOL_LIST_MSG = "val.empty.pool.list";

    public static final String EMPTY_POOL_METRICS_MSG = "val.pool.metrics.not.exist";

    public static final String EMPTY_MODE_SETTINGS_MSG = "val.empty.mode.settings";

    public static final String FIRMWARE_VERSION_ALREADY_REGISTERED_MSG = "val.firmware.version.already.registered";

    public static final String FIRMWARE_REGISTRATION_SUCCESS_MSG = "val.firmware.registration.success";

    public static final String NO_FIRMWARE_UPDATE_AVAILABLE_MSG = "val.no.firmware.update.available";

    public static final String FIRMWARE_UPDATE_FAILED_MSG = "Firmware update failed";


}
