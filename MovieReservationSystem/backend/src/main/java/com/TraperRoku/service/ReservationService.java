package com.TraperRoku.service;

import com.TraperRoku.entity.MovieSchedule;
import com.TraperRoku.entity.Reservation;
import com.TraperRoku.entity.Seat;
import com.TraperRoku.entity.User;
import com.TraperRoku.enums.ReservationStatus;
import com.TraperRoku.enums.SeatStatus;
import com.TraperRoku.repository.MovieScheduleRepository;
import com.TraperRoku.repository.ReservationRepository;
import com.TraperRoku.repository.SeatRepository;
import com.TraperRoku.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final MovieScheduleRepository movieScheduleRepository;
    private final UserRepository userRepository;


    public Reservation getReservationById(Long requestReservationId){
        return reservationRepository.findById(requestReservationId).orElseThrow(()-> new IllegalArgumentException("There is not reservation with that id"));
    }

    @Transactional
    public Reservation createReservation(Long userId, Long movieScheduleId, List<Long> seatIds) {
        // Sprawdź czy seans istnieje
        MovieSchedule schedule = movieScheduleRepository.findById(movieScheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono seansu"));

        // Sprawdź czy seans nie jest w przeszłości
        if (schedule.getShowTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Nie można zarezerwować miejsc na seans, który już się odbył");
        }

        // Znajdź użytkownika
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        // Znajdź miejsca dla tego konkretnego seansu
        List<Seat> seats = seatRepository.findAllByMovieScheduleIdAndIdIn(schedule.getId(), seatIds);

        // Sprawdź czy wszystkie miejsca zostały znalezione
        if (seats.size() != seatIds.size()) {
            throw new IllegalArgumentException("Niektóre miejsca nie zostały znalezione dla tego seansu");
        }


        // Ustaw status miejsc na PENDING
        seats.forEach(seat -> {
            seat.setStatus(SeatStatus.PENDING);
            seat.setLockedUntil(LocalDateTime.now().plusMinutes(10));
        });
        seatRepository.saveAll(seats);

        // Utwórz rezerwację
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setMovieSchedule(schedule);
        reservation.setSeats(seats);
        reservation.setCreatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        List<Seat> seats = reservation.getSeats();
        seats.forEach(seat -> seat.setStatus(SeatStatus.AVAILABLE));
        seatRepository.saveAll(seats);
    }

    @Transactional
    public void confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono rezerwacji"));

        // Potwierdź rezerwację
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);

        // Zaktualizuj status miejsc tylko dla tego konkretnego seansu
        List<Seat> seats = reservation.getSeats();
        seats.forEach(seat -> {
            if (seat.getMovieSchedule().getId().equals(reservation.getMovieSchedule().getId())) {
                seat.setStatus(SeatStatus.RESERVED);
                seat.setLockedUntil(null);
            }
        });

        seatRepository.saveAll(seats);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservation(Long userId){
        return reservationRepository.findByUserId(userId);
    }


}
