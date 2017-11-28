package de.yannickmortier.authserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RefreshTokenHolder {
    @JsonProperty("refresh_token")
    @NotNull
    @Size(min=10)
    private String refreshToken;
}
