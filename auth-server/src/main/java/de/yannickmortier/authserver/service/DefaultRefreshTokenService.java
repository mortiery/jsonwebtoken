package de.yannickmortier.authserver.service;

import de.yannickmortier.authserver.domain.RefreshToken;
import de.yannickmortier.authserver.domain.User;
import de.yannickmortier.authserver.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultRefreshTokenService implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findById(UUID id) {
        return refreshTokenRepository.findOne(id);
    }

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public List<RefreshToken> getRefreshTokensForUser(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    @Override
    public void deleteTokenForUser(User user, UUID tokenId) {
        RefreshToken tokenToDelete = refreshTokenRepository.findByUserAndId(user, tokenId);
        if (tokenToDelete == null) {
            throw new TokenNotFoundException();
        }
        refreshTokenRepository.delete(tokenToDelete);
    }

}
