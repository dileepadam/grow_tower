package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "GROW_TOWER_DATA")
public class GrowTowerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    private int deviceId;

    @Basic
    @Column(name = "serial_no", nullable = false, unique = true, length = 45)
    private String serialNo;

    @Basic
    @Column(name = "device_name", length = 45)
    private String deviceName;

    @Basic
    @Column(name = "device_type", length = 10)
    private String deviceType;

    @Basic
    @Column(name = "status", length = 10)
    private String status;

    @Basic
    @Column(name = "long")
    private Float longitude;

    @Basic
    @Column(name = "lat")
    private Float latitude;

    @Basic
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Basic
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
