package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class DeviceRegisterPK implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "mob_device_id")
    private Integer mobDeviceId;
}
