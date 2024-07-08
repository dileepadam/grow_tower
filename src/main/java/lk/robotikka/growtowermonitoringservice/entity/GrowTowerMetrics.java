package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "GROW_TOWER_METRICS")
public class GrowTowerMetrics {

    @Id
    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    private GrowTowerData device;

    @Basic
    @Column(name = "nutrition_lvl", length = 100)
    private Byte nutritionLvl;

    @Basic
    @Column(name = "water_lvl", length = 100)
    private Byte waterLvl;

    @Basic
    @Column(name = "water_temp", length = 5)
    private Byte waterTemp;

    @Basic
    @Column(name = "environment_temp", length = 5)
    private Byte environmentTemp;

    @Basic
    @Column(name = "environment_humidity", length = 10)
    private Byte environmentHumidity;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "last_updated_time")
    private LocalDateTime lastUpdatedTime;

}
