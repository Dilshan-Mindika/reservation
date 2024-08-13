package com.reservation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ReviewDTO {

    private long id;

    private String comment;

    private int rating;

    private UserDTO user;

    private LabDTO lab;

}
