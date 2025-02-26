package com.TraperRoku.service;

import com.TraperRoku.entity.Reservation;
import com.TraperRoku.entity.Ticket;
import com.TraperRoku.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public Ticket generateTicket(Reservation reservation) {


        Optional<Ticket> existingTicket = ticketRepository.findByReservationId(reservation.getId());

        if (existingTicket.isPresent()) {
            System.out.println("⚠️ Bilet dla tej rezerwacji już istnieje. Pomijam generowanie.");
            return existingTicket.get();
        }
        System.out.println("🟢 Generowanie biletu dla rezerwacji ID: " + reservation.getId());

        Ticket ticket = new Ticket();
        ticket.setReservation(reservation);
        ticket.setTicketNumber(UUID.randomUUID().toString());
        ticket.setIssuedAt(LocalDateTime.now());
        ticket.setTitleMovie(reservation.getMovieSchedule().getMovie().getTitle());

        Ticket savedTicket = ticketRepository.save(ticket);

        System.out.println("🟢 Bilet zapisany w bazie! Numer biletu: " + savedTicket.getTicketNumber());
        return savedTicket;
    }

    public Ticket getTicketByNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public Ticket getTicketByReservationId(Long reservationId) {
        return ticketRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
    public List<Ticket> getTicketByUser(Long userId){
        return ticketRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
