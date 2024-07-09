package lk.robotikka.growtowermonitoringservice.service;

import lk.robotikka.growtowermonitoringservice.domain.request.DeviceRegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public interface GrowTowerService {
    ResponseEntity<Object> registerDevice(DeviceRegisterRequest deviceRegisterRequest, Locale locale);
}
