package com.TraperRoku.service;

import com.TraperRoku.dto.PaymentConfirmationDto;
import com.TraperRoku.dto.PaymentRequestDto;
import com.TraperRoku.entity.Payment;
import com.TraperRoku.entity.Reservation;
import com.TraperRoku.entity.Ticket;
import com.TraperRoku.enums.PaymentStatus;
import com.TraperRoku.enums.ReservationStatus;
import com.TraperRoku.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ReservationService reservationService;
    private final PaymentRepository paymentRepository;
    private final TicketService ticketService;
    private final SeatService seatService;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Transactional
    public String createPaymentIntent(PaymentRequestDto request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        Reservation reservation = reservationService.getReservationById(request.getReservationId());
        double totalAmount = reservation.getTotalPrice() * 100;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) totalAmount)
                .setCurrency("pln")
                .putMetadata("reservationId", reservation.getId().toString())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return paymentIntent.getClientSecret();
    }

    @Transactional
    public Payment confirmPayment(Long reservationId, PaymentConfirmationDto confirmation) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);


        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(reservation.getTotalPrice());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.valueOf(confirmation.getStatus().toUpperCase()));
        Payment savedPayment = paymentRepository.save(payment);
        Ticket ticket = ticketService.generateTicket(reservation);
        seatService.reserveSeats(reservation.getSeats());


        System.out.println("🟢 Płatność zapisana w bazie!");

        System.out.println("🎟️ Wygenerowano bilet: " + ticket.getTicketNumber());


        return savedPayment;
    }
}