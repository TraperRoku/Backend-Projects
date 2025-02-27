package com.TraperRoku.service;

import com.TraperRoku.entity.Seat;
import com.TraperRoku.enums.SeatStatus;
import com.TraperRoku.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    // Pobiera miejsca dla konkretnego seansu
    public List<Seat> getSeatsByMovieSchedule(Long movieScheduleId) {
        return seatRepository.findByMovieScheduleId(movieScheduleId);
    }

    @Transactional
    public void reserveSeatsForSchedule(Long movieScheduleId, List<Long> seatIds) {
        List<Seat> seats = seatRepository.findByMovieScheduleIdAndIdIn(movieScheduleId, seatIds);

        if (seats.size() != seatIds.size()) {
            throw new IllegalArgumentException("Some seats not found for this schedule");
        }

        seats.forEach(seat -> {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalStateException(
                        String.format("Seat %d in row %d is not available",
                                seat.getSeatNumber(), seat.getRowNumber())
                );
            }
        });

        seats.forEach(seat -> seat.setStatus(SeatStatus.RESERVED));
        seatRepository.saveAll(seats);
    }



    @Transactional
    public void reserveSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.RESERVED) {
                throw new RuntimeException("Seat " + seat.getId() + " is already reserved");
            }
            seat.setStatus(SeatStatus.RESERVED);
        }
        seatRepository.saveAll(seats);
    }


    @Transactional
    public void blockSeats(Long movieScheduleId, List<Long> seatIds) {
        List<Seat> seats = seatRepository.findAllById(seatIds);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lockedUntil = now.plusMinutes(10);

        for (Seat seat : seats) {
            if (seat.getStatus() != SeatStatus.AVAILABLE && seat.getLockedUntil() != null && seat.getLockedUntil().isAfter(now)) {
                throw new IllegalStateException("Miejsce nie jest dostępne.");
            }
            seat.setStatus(SeatStatus.PENDING);
            seat.setLockedUntil(lockedUntil);
        }
        seatRepository.saveAll(seats);
    }

    @Transactional
    public void unblockSeats(Long movieScheduleId, List<Long> seatIds) {
        List<Seat> seats = seatRepository.findAllById(seatIds);
        seats.forEach(seat -> {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedUntil(null);
        });
        seatRepository.saveAll(seats);
    }


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