package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.type.descriptor.jdbc.TinyIntAsSmallIntJdbcType;

@Data
@Entity
@Table(name = "COMMON_CONFIG")
public class CommonConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_config", nullable = false)
    private int idConfig;

    @Basic
    @Column(name = "max_water_lvl")
    private byte maxWaterLvl;

    @Basic
    @Column(name = "min_water_lvl")
    private byte minWaterLvl;

    @Basic
    @Column(name = "min_water_temp")
    private byte minWaterTemp;

    @Basic
    @Column(name = "max_water_temp")
    private byte maxWaterTemp;

}
