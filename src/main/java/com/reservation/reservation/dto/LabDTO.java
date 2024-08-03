package com.reservation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class LabDTO {

    private long id;
    private String labType;
    private int labCapacity;
    private String labPhotoUrl;
    private String labDescription;
    private List<ReservingDTO> reservings;

}
