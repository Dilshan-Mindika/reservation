package com.reservation.reservation.service.impl;

import com.reservation.reservation.dto.LabDTO;
import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Lab;
import com.reservation.reservation.exception.OurException;
import com.reservation.reservation.repo.LabRepository;
import com.reservation.reservation.repo.ReservingRepository;
import com.reservation.reservation.service.AwsS3Service;
import com.reservation.reservation.service.interfac.ILabService;
import com.reservation.reservation.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class LabService implements ILabService {

    @Autowired
    private LabRepository labRepository;
    @Autowired
    private ReservingRepository reservingRepository;
    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewLab(MultipartFile photo, String labType, int labCapacity, String description) {
        Response response = new Response();
        try {
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Lab lab = new Lab();
            lab.setLabPhotoUrl(imageUrl);
            lab.setLabType(labType);
            lab.setLabCapacity(labCapacity);
            lab.setLabDescription(description);
            Lab savedLab = labRepository.save(lab);
            LabDTO labDTO = Utils.mapLabEntityToLabDTO(savedLab);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setLab(labDTO);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a lab: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllLabTypes() {
        return labRepository.findDistinctLabTypes();
    }

    @Override
    public Response getAllLabs() {
        Response response = new Response();
        try {
            List<Lab> labList = labRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<LabDTO> labDTOList = Utils.mapLabListEntityToLabListDTO(labList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setLabList(labDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all labs: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteLab(Long labId) {
        Response response = new Response();
        try {
            labRepository.findById(labId).orElseThrow(() -> new OurException("Lab Not Found"));
            labRepository.deleteById(labId);
            response.setStatusCode(200);
            response.setMessage("successful");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting a lab: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateLab(Long labId, String description, String labType, int labCapacity, MultipartFile photo) {
        Response response = new Response();
        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            Lab lab = labRepository.findById(labId).orElseThrow(() -> new OurException("Lab Not Found"));
            if (labType != null) lab.setLabType(labType);
            if (labCapacity > 0) lab.setLabCapacity(labCapacity);
            if (description != null) lab.setLabDescription(description);
            if (imageUrl != null) lab.setLabPhotoUrl(imageUrl);

            Lab updatedLab = labRepository.save(lab);
            LabDTO labDTO = Utils.mapLabEntityToLabDTO(updatedLab);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setLab(labDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating a lab: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getLabById(Long labId) {
        Response response = new Response();
        try {
            Lab lab = labRepository.findById(labId).orElseThrow(() -> new OurException("Lab Not Found"));
            LabDTO labDTO = Utils.mapLabEntityToLabDTOPlusReservings(lab);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setLab(labDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting lab by ID: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableLabsByDataAndType(LocalDate checkInDate, LocalTime checkInTime, String labType) {
        Response response = new Response();
        try {
            List<Lab> availableLabs = labRepository.findAvailableLabsByDatesAndTypes(checkInDate, checkInTime, labType);
            List<LabDTO> labDTOList = Utils.mapLabListEntityToLabListDTO(availableLabs);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setLabList(labDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting available labs by date and type: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableLabs() {
        Response response = new Response();
        try {
            List<Lab> labList = labRepository.getAllAvailableLabs();
            List<LabDTO> labDTOList = Utils.mapLabListEntityToLabListDTO(labList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setLabList(labDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all available labs: " + e.getMessage());
        }
        return response;
    }
}
