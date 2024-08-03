package com.reservation.reservation.repo;

import com.reservation.reservation.entity.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LabRepository extends JpaRepository<Lab, Long> {

    @Query("SELECT DISTINCT l.labType From Lab l")
    List<String> findDistinctLabTypes();

    @Query("SELECT l FROM Lab l WHERE l.labType LIKE %:labType% AND l.id NOT IN (SELECT rt.lab.id FROM Reserving rt WHERE rt.checkInDate = :checkInDate AND rt.checkInTime = :checkInTime)")
    List<Lab> findAvailableLabsByDatesAndTypes(LocalDate checkInDate, LocalTime checkInTime, String labType);

    @Query("SELECT l FROM Lab l WHERE l.id NOT IN (SELECT l.lab.id FROM Reserving l)")
    List<Lab> getAllAvailableLabs();
}
