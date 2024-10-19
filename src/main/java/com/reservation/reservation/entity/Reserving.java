package com.reservation.reservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "reservings")

public class Reserving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "check in date is required")
    @Future
    private LocalDate checkInDate;

    @Future(message = "check in time is required")
    private LocalTime checkInTime;

    @Future(message = "check out time is required")
    private LocalTime checkOutTime;

    @Min(value = 5, message = "Number of Students must not be less than 5")
    private int totalNumOfStudents;

    private String reservingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id,")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id,")
    private Lab lab;


    @Override
    public String toString() {
        return "Reserving{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", totalNumOfStudents=" + totalNumOfStudents +
                ", reservingConfirmationCode='" + reservingConfirmationCode + '\'' +
                '}';
    }
}
