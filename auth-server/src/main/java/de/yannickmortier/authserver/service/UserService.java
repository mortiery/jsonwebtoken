package de.yannickmortier.authserver.service;

import de.yannickmortier.authserver.domain.User;

import java.util.UUID;

public interface UserService {
    User findByUserName(String userName);
    User findById(UUID id);
}
