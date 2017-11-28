package de.yannickmortier.productservice.jwtsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TokenHandler {

    private final SigningKeyResolver signingKeyResolver;

    Jws<Claims> parseJwtToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token);

        if (jws.getHeader().containsKey("rfrsh")) {
            throw new IllegalArgumentException("Refresh token not accepted as login token!");
        }
        return jws;
    }

}
