package lk.robotikka.growtowermonitoringservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lk.robotikka.growtowermonitoringservice.domain.request.DeviceRegisterRequest;
import lk.robotikka.growtowermonitoringservice.domain.Response;
import lk.robotikka.growtowermonitoringservice.dto.DeviceRegisterRequestDTO;
import lk.robotikka.growtowermonitoringservice.service.GrowTowerService;
import lk.robotikka.growtowermonitoringservice.util.EndPoint;
import lk.robotikka.growtowermonitoringservice.util.Utility;
import lk.robotikka.growtowermonitoringservice.validator.group.OrderedCheck;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/device")
@CrossOrigin
@Validated
public class DeviceController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GrowTowerService growTowerService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = EndPoint.DEVICE_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Grow Tower Register", description = "Register Device to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Registered", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> login(
            @Validated(OrderedCheck.class) @RequestBody DeviceRegisterRequestDTO deviceRegisterRequestDTO, Locale locale)
            throws Exception {
        logger.debug("Received Login Request - {} ", Utility.objectToJson(deviceRegisterRequestDTO));
        return growTowerService.registerDevice(modelMapper.map(deviceRegisterRequestDTO, DeviceRegisterRequest.class), locale);

    }
}
