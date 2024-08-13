package com.reservation.reservation.service.impl;

import com.reservation.reservation.dto.ReservingDTO;
import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Lab;
import com.reservation.reservation.entity.Reserving;
import com.reservation.reservation.entity.User;
import com.reservation.reservation.exception.OurException;
import com.reservation.reservation.repo.LabRepository;
import com.reservation.reservation.repo.ReservingRepository;
import com.reservation.reservation.repo.UserRepository;
import com.reservation.reservation.service.interfac.ILabService;
import com.reservation.reservation.service.interfac.IReservingService;
import com.reservation.reservation.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservingService implements IReservingService {

    @Autowired
    private ReservingRepository reservingRepository;
    @Autowired
    private ILabService labService;
    @Autowired
    private LabRepository labRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveReserving(Long labId, Long userId, Reserving reservingRequest) {

        Response response = new Response();

        try {
            if (reservingRequest.getCheckInDate().isBefore(LocalDate.now())) { // Check if check-in date is in the past
                throw new IllegalArgumentException("Check in date must be in the future");
            }

            Lab lab = labRepository.findById(labId).orElseThrow(() -> new OurException("Lab Not Found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            List<Reserving> existingReservings = lab.getReservings();  // Assuming the relationship is called `reservings`

            if (!labIsAvailable(reservingRequest, existingReservings)) {
                throw new OurException("Lab not Available for selected date and time");
            }

            reservingRequest.setLab(lab);
            reservingRequest.setUser(user);
            String reservingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            reservingRequest.setReservingConfirmationCode(reservingConfirmationCode);
            reservingRepository.save(reservingRequest);  // Assuming you have a `reservingRepository`
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setReservingConfirmationCode(reservingConfirmationCode);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Saving a reserving: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response findReservingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Reserving reserving = reservingRepository.findByReservingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new OurException("Reserving Not Found"));
            ReservingDTO reservingDTO = Utils.mapReservingEntityToReservingDTOPlusReservedLabs(reserving, true);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReserving(reservingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error finding a reserving: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllReservings() {
        Response response = new Response();

        try {
            List<Reserving> reservingList = reservingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<ReservingDTO> reservingDTOList = Utils.mapReservingListEntityToReservingListDTO(reservingList);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setReservingList(reservingDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all reservings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelReserving(Long reservingId) {
        Response response = new Response();

        try {
            reservingRepository.findById(reservingId).orElseThrow(() -> new OurException("Reserving Does Not Exist"));
            reservingRepository.deleteById(reservingId);
            response.setStatusCode(200);
            response.setMessage("Successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error cancelling a reserving: " + e.getMessage());

        }
        return response;
    }

    private boolean labIsAvailable(Reserving reservingRequest, List<Reserving> existingReservings) {
        LocalTime requestedCheckInTime = reservingRequest.getCheckInTime();
        LocalTime requestedCheckOutTime = reservingRequest.getCheckOutTime();
        LocalDate requestedCheckInDate = reservingRequest.getCheckInDate();

        for (Reserving existingReserving : existingReservings) {
            // Check for conflicts based on check-in date and time
            if (existingReserving.getCheckInDate().equals(requestedCheckInDate) &&
                    ((existingReserving.getCheckInTime().isBefore(requestedCheckInTime) &&
                            existingReserving.getCheckInTime().plusHours(1).isAfter(requestedCheckInTime)) ||
                            (existingReserving.getCheckInTime().equals(requestedCheckInTime)))) {
                return false; // Overlap detected
            }
        }
        return true; // No overlap found, lab is available
    }

}
