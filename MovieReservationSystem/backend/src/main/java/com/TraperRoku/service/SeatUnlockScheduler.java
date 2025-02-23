package com.TraperRoku.service;

import com.TraperRoku.entity.Seat;
import com.TraperRoku.enums.SeatStatus;
import com.TraperRoku.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatUnlockScheduler {

    private final SeatRepository seatRepository;

    @Scheduled(fixedRate = 60000) // Sprawdzaj co minutę
    @Transactional
    public void unlockExpiredSeats() {
        LocalDateTime now = LocalDateTime.now();
        List<Seat> seats = seatRepository.findByStatusAndLockedUntilBefore(SeatStatus.PENDING, now);

        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedUntil(null);
        }
        seatRepository.saveAll(seats);
    }
}