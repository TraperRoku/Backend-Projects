package com.TraperRoku.controller;

import com.TraperRoku.entity.Ticket;
import com.TraperRoku.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{ticketNumber}")
    public Ticket getTicketByNumber(@PathVariable String ticketNumber){
        return ticketService.getTicketByNumber(ticketNumber);
    }

    @GetMapping("/reservation/{reservationId}")
    public Ticket getTicketByReservation(@PathVariable Long reservationId) {
        return  ticketService.getTicketByReservationId(reservationId);
    }
}
