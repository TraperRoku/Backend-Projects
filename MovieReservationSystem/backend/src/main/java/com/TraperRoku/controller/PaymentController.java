package com.TraperRoku.controller;

import com.TraperRoku.dto.PaymentConfirmationDto;
import com.TraperRoku.dto.PaymentRequestDto;
import com.TraperRoku.service.PaymentService;
import com.TraperRoku.service.ReservationService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private  final ReservationService reservationService;

    @PostMapping("/create-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequestDto request){
        try{
            String clientSecret = paymentService.createPaymentIntent(request);
            return ResponseEntity.ok(clientSecret);
        }catch (StripeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("confirm/{reservationId}")
    public ResponseEntity<?> confirmPayment(@PathVariable Long reservationId, @RequestBody PaymentConfirmationDto confirmation){
        return ResponseEntity.ok(paymentService.confirmPayment(reservationId,confirmation));
    }
}
