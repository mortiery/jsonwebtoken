package de.yannickmortier.productservice.service;

import de.yannickmortier.productservice.domain.User;

import java.util.UUID;

public interface UserService {
    User findOrCreate(UUID authId);
}
