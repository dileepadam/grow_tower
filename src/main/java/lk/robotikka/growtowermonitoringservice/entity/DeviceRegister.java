package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DEVICE_REGISTER")
public class DeviceRegister {

    @EmbeddedId
    private DeviceRegisterPK deviceRegisterPK;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private UserData user;

    @ManyToOne
    @MapsId("deviceId")
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false, insertable = false, updatable = false)
    private GrowTowerData device;

    @ManyToOne
    @MapsId("mobDeviceId")
    @JoinColumn(name = "mob_device_id", referencedColumnName = "mob_device_id", nullable = false, insertable = false, updatable = false)
    private MobileDevice mobileDevice;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
