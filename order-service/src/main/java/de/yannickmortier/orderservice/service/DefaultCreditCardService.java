package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.dto.CreditCardDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultCreditCardService  implements  CreditCardService {
    @Override
    public boolean processOrder(CreditCardDto creditCard, BigDecimal amount) {
        try {
            // Simulate processing credit card...
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
