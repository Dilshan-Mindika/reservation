package com.reservation.reservation.service.interfac;

import com.reservation.reservation.dto.Response;
import com.reservation.reservation.entity.Reserving;

public interface IReservingService {

    Response saveReserving(Long labId, Long userId, Reserving reservingRequest);

    Response findReservingByConfirmationCode(String confirmationCode);

    Response getAllReservings();

    Response cancelReserving(Long reservingId);

}
