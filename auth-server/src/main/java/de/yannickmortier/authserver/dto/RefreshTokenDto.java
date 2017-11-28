package de.yannickmortier.authserver.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RefreshTokenDto {
    private UUID id;
    private UUID userId;
    private boolean valid;
    private Instant expires;
    private String remoteIp;
}
