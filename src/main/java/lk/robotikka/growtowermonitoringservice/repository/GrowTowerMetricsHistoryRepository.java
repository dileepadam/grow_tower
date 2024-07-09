package lk.robotikka.growtowermonitoringservice.repository;

import lk.robotikka.growtowermonitoringservice.entity.GrowTowerMetricsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowTowerMetricsHistoryRepository extends JpaRepository<GrowTowerMetricsHistory, Integer> {
}
