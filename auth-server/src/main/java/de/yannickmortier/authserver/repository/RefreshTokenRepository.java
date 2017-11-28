package de.yannickmortier.authserver.repository;

import de.yannickmortier.authserver.domain.RefreshToken;
import de.yannickmortier.authserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    List<RefreshToken> findByUser(User user);
    RefreshToken findByUserAndId(User user, UUID tokenId);
}
