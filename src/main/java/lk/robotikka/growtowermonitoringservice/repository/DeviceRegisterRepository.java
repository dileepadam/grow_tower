package lk.robotikka.growtowermonitoringservice.repository;

import lk.robotikka.growtowermonitoringservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRegisterRepository extends JpaRepository<DeviceRegister, DeviceRegisterPK> {
    @Query("SELECT dr FROM DeviceRegister dr JOIN dr.user u WHERE u.mobileNo = :mobileNo")
    List<DeviceRegister> findByUserMobileNo(@Param("mobileNo") int mobileNo);

    DeviceRegister findByUserAndDeviceAndMobileDevice(UserData suer, GrowTowerData device, MobileDevice mobileDevice);

    @Query("SELECT dr.mobileDevice FROM DeviceRegister dr WHERE dr.device.serialNo = :serialNo")
    List<MobileDevice> findAllRegisteredMobileDevicesByGrowTowerSerialNo(@Param("serialNo") String serialNo);
}
