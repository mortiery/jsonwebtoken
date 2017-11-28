package de.yannickmortier.authserver.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private boolean valid;

    @Column(nullable = false)
    private Instant expires;

    @Column(nullable = false)
    private String remoteIp;

    @Column(nullable = false)
    private UUID keyId;
}
