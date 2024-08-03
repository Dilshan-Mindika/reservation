package com.reservation.reservation.controller;

import com.reservation.reservation.dto.Response;
import com.reservation.reservation.service.interfac.IReservingService;
import com.reservation.reservation.service.interfac.ILabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/labs")

public class LabController {

    @Autowired
    private ILabService labService;

    @Autowired
    private IReservingService reservingService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewLab(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "labType", required = false) String labType,
            @RequestParam(value = "labCapacity", required = false) Integer labCapacity,
            @RequestParam(value = "labDescription", required = false) String labDescription
    ) {
        if (photo == null || photo.isEmpty() || labType == null || labType.isBlank() || labCapacity == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (photo, labType, labCapacity)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = labService.addNewLab(photo, labType, labCapacity, labDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllLabs() {
        Response response = labService.getAllLabs();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getLabTypes() {
        return labService.getAllLabTypes();
    }

    @GetMapping("/lab-by-id/{labId}")
    public ResponseEntity<Response> getLabById(@PathVariable Long labId) {
        Response response = labService.getLabById(labId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-labs")
    public ResponseEntity<Response> getAvailableLabs() {
        Response response = labService.getAllAvailableLabs();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-labs-by-date-and-type")
    public ResponseEntity<Response> getAvailableLabsByDateAndType(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime checkInTime,
            @RequestParam String labType
    ) {
        if (checkInDate == null || labType == null || labType.isBlank() || checkInTime == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (checkInDate, labType, checkInTime)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = labService.getAvailableLabsByDataAndType(checkInDate, checkInTime, labType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{labId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateLab(@PathVariable Long labId,
                                              @RequestParam(value = "photo", required = false) MultipartFile photo,
                                              @RequestParam(value = "labType", required = false) String labType,
                                              @RequestParam(value = "labCapacity", required = false) Integer labCapacity,
                                              @RequestParam(value = "labDescription", required = false) String labDescription)
    {
        Response response = labService.updateLab(labId, labDescription, labType, labCapacity, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{labId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteLab(@PathVariable Long labId) {
        Response response = labService.deleteLab(labId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
