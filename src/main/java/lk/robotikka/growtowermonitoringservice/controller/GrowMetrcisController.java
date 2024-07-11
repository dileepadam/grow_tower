package lk.robotikka.growtowermonitoringservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lk.robotikka.growtowermonitoringservice.domain.Response;
import lk.robotikka.growtowermonitoringservice.domain.request.DeviceRegisterRequest;
import lk.robotikka.growtowermonitoringservice.domain.request.GetGrowTowerDataRequest;
import lk.robotikka.growtowermonitoringservice.dto.DeviceRegisterRequestDTO;
import lk.robotikka.growtowermonitoringservice.dto.GetGrowTowerDataRequestDTO;
import lk.robotikka.growtowermonitoringservice.service.GrowTowerMetricsService;
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
@RequestMapping("/api/v1/metrics")
@CrossOrigin
@Validated
public class GrowMetrcisController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GrowTowerMetricsService growTowerMetricsService;


    @PostMapping(value = EndPoint.GET_GROW_TOWER_METRICS, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Grow Tower Metrics", description = "Api get grow tower metrics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Grow Tower Data Successful", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> login(
            @Validated(OrderedCheck.class) @RequestBody GetGrowTowerDataRequestDTO getGrowTowerDataRequestDTO, Locale locale)
            throws Exception {
        logger.debug("Received Login Request - {} ", Utility.objectToJson(getGrowTowerDataRequestDTO));
        return growTowerMetricsService.getDeviceData(modelMapper.map(getGrowTowerDataRequestDTO, GetGrowTowerDataRequest.class), locale);

    }
}
