package lk.robotikka.growtowermonitoringservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lk.robotikka.growtowermonitoringservice.domain.request.LoginRequest;
import lk.robotikka.growtowermonitoringservice.domain.Response;
import lk.robotikka.growtowermonitoringservice.domain.request.UserRegisterRequest;
import lk.robotikka.growtowermonitoringservice.domain.response.LoginResponse;
import lk.robotikka.growtowermonitoringservice.dto.LoginRequestDTO;
import lk.robotikka.growtowermonitoringservice.dto.UserRegisterRequestDTO;
import lk.robotikka.growtowermonitoringservice.service.UserService;
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
@RequestMapping("/api/v1/user")
@CrossOrigin
@Validated
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = EndPoint.USER_REGISTRATION, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "User Signup", description = "Register a user to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully signup", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> deviceRegistration(
            @Validated(OrderedCheck.class) @RequestBody UserRegisterRequestDTO requestDTO, Locale locale)
            throws Exception {
        logger.debug("Received Signup Request - {} ", Utility.objectToJson(requestDTO));
        return userService.createUser(modelMapper.map(requestDTO, UserRegisterRequest.class), locale);
    }

    @PostMapping(value = EndPoint.LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "User Login", description = "Login a user to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Login", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> login(
            @Validated(OrderedCheck.class) @RequestBody LoginRequestDTO loginRequestDTO, Locale locale)
            throws Exception {
        logger.debug("Received Login Request - {} ", Utility.objectToJson(loginRequestDTO));
        return userService.login(modelMapper.map(loginRequestDTO, LoginRequest.class), locale);
    }
}
