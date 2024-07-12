package lk.robotikka.growtowermonitoringservice.repository;

import lk.robotikka.growtowermonitoringservice.entity.GrowTowerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrowTowerDataRepository extends JpaRepository<GrowTowerData, Integer> {
    Optional<GrowTowerData> findBySerialNo(String serialNo);

}
