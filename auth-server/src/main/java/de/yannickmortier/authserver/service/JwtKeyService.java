package de.yannickmortier.authserver.service;


import de.yannickmortier.authserver.domain.JwtKey;

import java.util.UUID;

public interface JwtKeyService {
    JwtKey findById(UUID id);
    void save(JwtKey jwtKey);
}
