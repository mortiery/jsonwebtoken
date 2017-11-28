package de.yannickmortier.authserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtKey {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private byte[] encodedKey;
}
