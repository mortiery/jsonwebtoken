package de.yannickmortier.cartservice.service;


import de.yannickmortier.cartservice.domain.User;

import java.util.UUID;

public interface UserService {
    User findOrCreate(UUID authId);
    User findOne(UUID userId);
    User saveAndFlush(User user);
}
