package lk.robotikka.growtowermonitoringservice.repository;

import lk.robotikka.growtowermonitoringservice.entity.GrowTowerMetrics;
import lk.robotikka.growtowermonitoringservice.entity.GrowTowerMetricsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowTowerMetricsRepository extends JpaRepository<GrowTowerMetrics, GrowTowerMetricsPK> {
}
