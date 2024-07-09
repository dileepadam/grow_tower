package lk.robotikka.growtowermonitoringservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lk.robotikka.growtowermonitoringservice.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER_DATA")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic
    @Column(name = "mobile_no", nullable = false, unique = true)
    private int mobileNo;

    @Basic
    @Column(name = "password", length = 255)
    private String password;

    @Basic
    @Column(name = "status", length = 10)
    private String status;

    @Basic
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Basic
    @Column(name = "last_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdatedDate;

    @Basic
    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedDate;
}
