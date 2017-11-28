package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.dto.CreditCardDto;

import java.math.BigDecimal;

public interface CreditCardService {
    boolean processOrder(CreditCardDto creditCard, BigDecimal amount);
}
