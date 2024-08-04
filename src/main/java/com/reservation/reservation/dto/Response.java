package com.reservation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String reservingConfirmationCode;

    private UserDTO user;
    private LabDTO lab;
    private ReservingDTO reserving;
    private List<UserDTO> userList;
    private List<LabDTO> labList;
    private List<ReservingDTO> reservingList;
}
