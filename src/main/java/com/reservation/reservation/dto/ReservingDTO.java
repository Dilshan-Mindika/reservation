package com.reservation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ReservingDTO {

    private long id;

    private LocalDate checkInDate;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private int totalNumOfStudents;

    private String reservingConfirmationCode;

    private UserDTO user;

    private LabDTO lab;
}
