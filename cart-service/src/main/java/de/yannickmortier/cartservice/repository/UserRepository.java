package de.yannickmortier.cartservice.repository;

import de.yannickmortier.cartservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByRemoteId(UUID remoteId);
}
