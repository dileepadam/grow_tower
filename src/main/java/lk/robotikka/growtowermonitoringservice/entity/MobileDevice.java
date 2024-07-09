package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOBILE_DEVICE")
public class MobileDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mob_device_id", nullable = false)
    private int mobDeviceId;

    @Basic
    @Column(name = "push_id", nullable = false, length = 45)
    private String pushId;

    @Basic
    @Column(name = "uuid", nullable = false, length = 45)
    private String uuid;

    @Basic
    @Column(name = "os_type", length = 45)
    private String osType;

    @Basic
    @Column(name = "os_version", length = 45)
    private String osVersion;

    @Basic
    @Column(name = "status", length = 45)
    private String status;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Basic
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}
