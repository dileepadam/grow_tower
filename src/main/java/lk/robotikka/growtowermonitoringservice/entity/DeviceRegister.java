package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DEVICE_REGISTER")
public class DeviceRegister {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserData user;

    @Id
    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    private GrowTowerData device;

    @Id
    @ManyToOne
    @JoinColumn(name = "mob_device_id", referencedColumnName = "mob_device_id", nullable = false)
    private MobileDevice mobileDevice;

    @Basic
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
