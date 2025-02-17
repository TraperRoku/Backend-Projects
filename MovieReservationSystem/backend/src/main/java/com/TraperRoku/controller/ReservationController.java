package com.TraperRoku.controller;

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
    public ResponseEntity<Reservation> createReservation(@RequestParam Long userId,
                                                         @RequestParam Long movieScheduleId,
                                                         @RequestParam List<Long> seatIds){
        Reservation reservation = reservationService.createReservation(userId, movieScheduleId, seatIds);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId){
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<Reservation> confirmReservation(@PathVariable Long reservationId){
        Reservation reservation = reservationService.confirmReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long userId){
        return ResponseEntity.ok(reservationService.getAllReservation(userId));
    }



}
