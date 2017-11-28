package de.yannickmortier.orderservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreditCardDto {
    @NotNull
    @Size(min = 4)
    private String nameOnCard;

    @NotNull
    @Size(min = 16, max = 16)
    private String creditCard;

    @NotNull
    @Size(min = 2, max = 2)
    private String expiryMonth;

    @NotNull
    @Size(min = 2, max = 2)
    private String expiryYear;
}
