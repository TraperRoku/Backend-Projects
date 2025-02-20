package com.TraperRoku.controller;

import com.TraperRoku.entity.Seat;
import com.TraperRoku.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieScheduleId}/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<Seat>> getSeatsByMovieSchedule(@PathVariable Long movieScheduleId) {
        List<Seat> seats = seatService.getSeatsByMovieSchedule(movieScheduleId);
        return ResponseEntity.ok(seats);
    }

    // Zmiana statusu miejsca (np. RESERVED)
    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<Seat> reserveSeat(@PathVariable Long seatId) {
        Seat reservedSeat = seatService.reserveSeat(seatId);
        return ResponseEntity.ok(reservedSeat);
    }

    // Zwolnienie miejsca (np. z RESERVED na AVAILABLE)
    @PostMapping("/{seatId}/cancel")
    public ResponseEntity<Seat> cancelReservation(@PathVariable Long seatId) {
        Seat availableSeat = seatService.cancelReservation(seatId);
        return ResponseEntity.ok(availableSeat);
    }
}