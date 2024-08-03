package com.reservation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude

public class UserDTO {

    private long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private List<ReservingDTO> reservings = new ArrayList<>();


}
