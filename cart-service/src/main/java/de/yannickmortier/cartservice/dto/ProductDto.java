package de.yannickmortier.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    @NotNull
    private UUID id;
}
