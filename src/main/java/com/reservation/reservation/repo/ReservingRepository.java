package com.reservation.reservation.repo;

import com.reservation.reservation.entity.Reserving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservingRepository extends JpaRepository<Reserving, Long> {

    Optional<Reserving> findByReservingConfirmationCode(String confirmationCode);
}
