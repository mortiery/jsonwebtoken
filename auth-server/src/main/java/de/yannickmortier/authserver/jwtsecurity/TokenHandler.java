package de.yannickmortier.authserver.jwtsecurity;

import de.yannickmortier.authserver.domain.RefreshToken;
import de.yannickmortier.authserver.domain.Role;
import de.yannickmortier.authserver.domain.User;
import de.yannickmortier.authserver.service.RefreshTokenService;
import de.yannickmortier.authserver.service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TokenHandler {

    private final UserService userService;
    private final KeyHolder keyHolder;
    private final SigningKeyResolver signingKeyResolver;
    private final RefreshTokenService refreshTokenService;

    User parseUserFromToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token);

        if (jws.getHeader().containsKey("rfrsh")) {
            throw new IllegalArgumentException("Refresh token not accepted as login token!");
        }

        UUID userId = UUID.fromString(jws.getBody().getSubject());

        return userService.findById(userId);
    }

    public String createTokenForRefreshToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token);

        if (!jws.getHeader().containsKey("rfrsh")) {
            throw new IllegalArgumentException("LoginCredentials token not accepted as refresh token!");
        }

        UUID refreshTokenId = UUID.fromString(jws.getBody().getId());

        RefreshToken refreshToken = refreshTokenService.findById(refreshTokenId);
        if (refreshToken == null || !refreshToken.isValid()) {
            throw new IllegalArgumentException("Refresh token has been deleted or invalidated!");
        }

        UUID userId = UUID.fromString(jws.getBody().getSubject());

        User refreshTokenUser = userService.findById(userId);

        if (refreshTokenUser == null) {
            throw new IllegalArgumentException("Refresh token user does not exist anymore!");
        }

        return createTokenForUser(refreshTokenUser);
    }

    public String createTokenForUser(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1L));
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getId().toString())
                .claim("usr", user.getUserName())
                .claim("rls", user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()))
                .setIssuedAt(now)
                .setHeader(getDefaultHeaderMap(false))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.RS512, keyHolder.getPrivateKey())
                .compact();
    }

    public String createRefreshTokenForUser(User user, String remoteIp) {
        Date now = new Date();
        Instant expiration = Instant.now().plusMillis(TimeUnit.DAYS.toMillis(30L));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setValid(true);
        refreshToken.setExpires(expiration);
        refreshToken.setRemoteIp(remoteIp);
        refreshToken.setKeyId(keyHolder.getCurrentKeyId());
        refreshTokenService.save(refreshToken);

        return Jwts.builder()
                .setId(refreshToken.getId().toString())
                .setSubject(user.getId().toString())
                .claim("usr", user.getUserName())
                .setIssuedAt(now)
                .setHeader(getDefaultHeaderMap(true))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.RS512, keyHolder.getPrivateKey())
                .compact();
    }

    private Map<String, Object> getDefaultHeaderMap(boolean isRefresh) {
        Map<String, Object> header = new HashMap<>();
        header.put("kid", keyHolder.getCurrentKeyId().toString());
        if (isRefresh) {
            header.put("rfrsh", true);
        }
        return header;
    }

}
