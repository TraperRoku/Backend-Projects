package com.TraperRoku.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticketNumber;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private String titleMovie;


    public Ticket(Reservation reservation) {
        this.ticketNumber = UUID.randomUUID().toString();
        this.reservation = reservation;
        this.issuedAt = LocalDateTime.now();
    }

    public String getMovieTitle() {
        return reservation.getMovieSchedule().getMovie().getTitle();
    }

    public LocalDateTime getShowTime() {
        return reservation.getMovieSchedule().getShowTime();
    }

    public String getSeatNumbers() {
        return reservation.getSeats().stream()
                .map(seat -> "Row " + seat.getRowNumber() + ", Seat " + seat.getSeatNumber())
                .collect(Collectors.joining(", "));
    }

    public String getUserEmail() {
        return reservation.getUser().getEmail();
    }
}