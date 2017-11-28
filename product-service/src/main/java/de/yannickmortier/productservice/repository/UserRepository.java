package de.yannickmortier.productservice.repository;

import de.yannickmortier.productservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByRemoteId(UUID remoteId);
}
