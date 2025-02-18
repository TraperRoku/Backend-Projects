package com.TraperRoku.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmationDto {
    private String paymentIntentId;
    private String paymentMethodId;
    private String confirmationToken;
}
