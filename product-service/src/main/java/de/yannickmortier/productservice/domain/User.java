package de.yannickmortier.productservice.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID remoteId;

    @Column
    @Setter(AccessLevel.NONE)
    private Instant created;

    @Transient
    private String currentToken;

    @PrePersist
    private void setCreationTime() {
        this.created = Instant.now();
    }
}
