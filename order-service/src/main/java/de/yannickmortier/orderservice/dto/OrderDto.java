package de.yannickmortier.orderservice.dto;

import de.yannickmortier.orderservice.domain.BoughtProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private Instant orderTime;
    private BigDecimal orderTotal;
    private String creditCardDigits;
    private List<BoughtProduct> boughtProducts;
}
