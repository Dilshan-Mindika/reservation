package com.reservation.reservation.controller;


import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Reserving;
import com.reservation.reservation.service.interfac.IReservingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservings")

public class ReservingController {

    @Autowired
    private IReservingService reservingService;

    @PostMapping("/reserve-lab/{labId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveReservings(@PathVariable Long labId,
                                                 @PathVariable Long userId,
                                                 @RequestBody Reserving reservingRequest) {


        Response response = reservingService.saveReserving(labId, userId, reservingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllReservings() {
        Response response = reservingService.getAllReservings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getReservingByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = reservingService.findReservingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/cancel/{reservingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelReserving(@PathVariable Long reservingId) {
        Response response = reservingService.cancelReserving(reservingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
