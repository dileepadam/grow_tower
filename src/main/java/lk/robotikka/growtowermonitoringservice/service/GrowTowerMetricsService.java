package lk.robotikka.growtowermonitoringservice.service;

import lk.robotikka.growtowermonitoringservice.domain.request.GetGrowTowerDataRequest;
import lk.robotikka.growtowermonitoringservice.dto.GetGrowTowerDataRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;
public interface GrowTowerMetricsService {
    ResponseEntity<Object> getDeviceData(GetGrowTowerDataRequest getGrowTowerDataRequest, Locale locale);
}
