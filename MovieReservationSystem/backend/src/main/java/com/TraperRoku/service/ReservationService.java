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
    public Reservation createReservation(Long userId, Long movieScheduleId, List<Long> seatIds){


/*
        List<Seat> seatsToReserve = seatRepository.findAllByMovieScheduleIdAndIdInAndStatus(movieScheduleId, seatIds, SeatStatus.AVAILABLE);
        if (seatsToReserve.size() != seatIds.size()) {
            throw new IllegalStateException("Niektóre miejsca są już zajęte!");
        }*/

        User user =  userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("There is not user with this id"));

        MovieSchedule movieSchedule = movieScheduleRepository.findById(movieScheduleId).orElseThrow(()->new IllegalArgumentException(
                "There is not movieSchedule with this id"
        ));
        List<Seat> seats = seatRepository.findAllById(seatIds);


        seats.forEach(seat -> seat.setStatus(SeatStatus.PENDING));
        seatRepository.saveAll(seats);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setMovieSchedule(movieSchedule);
        reservation.setSeats(seats);
        reservation.setCreatedAt(LocalDateTime.now());

        return  reservationRepository.save(reservation);
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
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);


        List<Seat> seats = reservation.getSeats();
        seats.forEach(seat -> seat.setStatus(SeatStatus.RESERVED));
        seatRepository.saveAll(seats);
    }
    public List<Reservation> getAllReservation(Long userId){
        return reservationRepository.findByUserId(userId);
    }


}
