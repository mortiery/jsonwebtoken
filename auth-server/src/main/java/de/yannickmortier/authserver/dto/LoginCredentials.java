package de.yannickmortier.authserver.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginCredentials {
    @NotNull
    @Size(min=2)
    private String username;
    @NotNull
    @Size(min=2)
    private String password;
}
