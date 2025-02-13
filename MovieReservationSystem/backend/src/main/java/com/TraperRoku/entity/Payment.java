package com.TraperRoku.entity;

import com.TraperRoku.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id",nullable = false)
    private Reservation reservation;

    private double amount;

    private LocalDateTime paymentDate;


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
