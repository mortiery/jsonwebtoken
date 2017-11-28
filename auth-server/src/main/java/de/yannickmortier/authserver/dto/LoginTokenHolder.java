package de.yannickmortier.authserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginTokenHolder {
    @JsonProperty("id_token")
    private String idToken;
}
