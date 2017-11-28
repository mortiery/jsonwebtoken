package de.yannickmortier.authserver.service;


import de.yannickmortier.authserver.domain.RefreshToken;
import de.yannickmortier.authserver.domain.User;

import java.util.List;
import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken findById(UUID id);
    void save(RefreshToken refreshToken);
    List<RefreshToken> getRefreshTokensForUser(User user);
    void deleteTokenForUser(User user, UUID tokenId);

    class TokenNotFoundException extends RuntimeException {

    }
}
