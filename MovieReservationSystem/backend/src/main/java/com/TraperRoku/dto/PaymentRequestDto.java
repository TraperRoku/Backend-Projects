package com.TraperRoku.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private Long reservationId;
    private double amount;
    private String currency;
}
