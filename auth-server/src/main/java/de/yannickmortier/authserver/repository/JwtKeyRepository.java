package de.yannickmortier.authserver.repository;


import de.yannickmortier.authserver.domain.JwtKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JwtKeyRepository extends JpaRepository<JwtKey, UUID> {
}
