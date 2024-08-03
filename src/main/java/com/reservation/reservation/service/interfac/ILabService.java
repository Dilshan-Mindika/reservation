package com.reservation.reservation.service.interfac;

import com.reservation.reservation.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ILabService {

    Response addNewLab(MultipartFile photo, String labType, int labCapacity, String description);

    List<String> getAllLabTypes();

    Response getAllLabs();

    Response deleteLab(Long labId);

    Response updateLab(Long labId, String description, String labType, int labCapacity, MultipartFile photo);

    Response getLabById(Long labId);

    Response getAvailableLabsByDataAndType(LocalDate checkInDate, LocalTime checkInTime, String labType);

    Response getAllAvailableLabs();
}
