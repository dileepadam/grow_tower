package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class GrowTowerMetricsPK implements Serializable {
    @Column(name = "device_id")
    private int deviceId;
}
