package de.yannickmortier.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductIdDto {
    @JsonProperty("productId")
    private UUID id;
}
