package de.yannickmortier.orderservice.service;



import de.yannickmortier.orderservice.domain.User;

import java.util.UUID;

public interface UserService {
    User findOrCreate(UUID authId);
    User findOne(UUID userId);
    User saveAndFlush(User user);
}
