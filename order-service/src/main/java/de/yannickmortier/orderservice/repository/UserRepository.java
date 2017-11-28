package de.yannickmortier.orderservice.repository;

import de.yannickmortier.orderservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByRemoteId(UUID remoteId);
}
