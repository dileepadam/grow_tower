package lk.robotikka.growtowermonitoringservice.repository;

import lk.robotikka.growtowermonitoringservice.entity.DeviceRegister;
import lk.robotikka.growtowermonitoringservice.entity.DeviceRegisterPK;
import lk.robotikka.growtowermonitoringservice.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRegisterRepository extends JpaRepository<DeviceRegister, DeviceRegisterPK> {
    DeviceRegister findByDeviceRegisterPKUserIdAndDeviceRegisterPKDeviceIdAndDeviceRegisterPKMobDeviceId(int userId, int deviceId, int mobDeviceId);

    @Query("SELECT dr FROM DeviceRegister dr JOIN dr.user u WHERE u.mobileNo = :mobileNo")
    List<DeviceRegister> findByUserMobileNo(@Param("mobileNo") int mobileNo);
}
