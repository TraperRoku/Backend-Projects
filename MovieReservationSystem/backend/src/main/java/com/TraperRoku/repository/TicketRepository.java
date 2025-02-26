package com.TraperRoku.repository;

import com.TraperRoku.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    Optional<Ticket> findByReservationId(Long reservationId);

    @Query("SELECT t FROM Ticket t WHERE t.reservation.user.id = :userId")
    Optional<List<Ticket>> findByUserId(@Param("userId") Long userId);
}
