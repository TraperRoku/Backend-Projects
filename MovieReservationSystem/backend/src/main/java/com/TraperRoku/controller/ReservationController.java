package com.TraperRoku.controller;

import com.TraperRoku.dto.CancelReservationDto;
import com.TraperRoku.entity.Reservation;
import com.TraperRoku.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestParam Long userId,
            @RequestParam Long movieScheduleId,
            @RequestParam(name = "seatIds") List<Long> seatIds) {

        Reservation reservation = reservationService.createReservation(userId, movieScheduleId, seatIds);
        return ResponseEntity.ok(reservation);
    }


    @PostMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok("Rezerwacja anulowana.");
    }
    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<String> confirmReservation(
            @PathVariable Long reservationId
    ) {
        reservationService.confirmReservation(reservationId);
        return ResponseEntity.ok("Rezerwacja potwierdzona.");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long userId){
        return ResponseEntity.ok(reservationService.getAllReservation(userId));
    }



}
