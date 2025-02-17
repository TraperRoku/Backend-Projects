package com.TraperRoku.service;

import com.TraperRoku.entity.Seat;
import com.TraperRoku.enums.SeatStatus;
import com.TraperRoku.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    // Pobiera miejsca dla konkretnego seansu
    public List<Seat> getSeatsByMovieSchedule(Long movieScheduleId) {
        return seatRepository.findByMovieScheduleId(movieScheduleId);
    }

    // Rezerwacja miejsca
    @Transactional
    public Seat reserveSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() == SeatStatus.RESERVED) {
            throw new RuntimeException("Seat is already reserved");
        }

        seat.setStatus(SeatStatus.RESERVED);
        return seatRepository.save(seat);
    }

    // Anulowanie rezerwacji miejsca
    @Transactional
    public Seat cancelReservation(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() == SeatStatus.AVAILABLE) {
            throw new RuntimeException("Seat is already available");
        }

        seat.setStatus(SeatStatus.AVAILABLE);
        return seatRepository.save(seat);
    }
}