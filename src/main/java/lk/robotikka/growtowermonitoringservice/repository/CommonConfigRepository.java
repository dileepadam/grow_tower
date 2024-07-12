package lk.robotikka.growtowermonitoringservice.repository;

import lk.robotikka.growtowermonitoringservice.entity.CommonConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonConfigRepository extends JpaRepository<CommonConfig, Integer> {
    CommonConfig findByIdConfig(int idConfig);
}
