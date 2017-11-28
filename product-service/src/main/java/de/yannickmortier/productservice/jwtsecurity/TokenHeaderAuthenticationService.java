package de.yannickmortier.productservice.jwtsecurity;

import de.yannickmortier.productservice.domain.User;
import de.yannickmortier.productservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TokenHeaderAuthenticationService {
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private final TokenHandler tokenHandler;
    private final UserService userService;

    Authentication getAuthentication(HttpServletRequest request) {
        final String tokenHeader = request.getHeader(AUTH_HEADER_NAME);
        if (tokenHeader == null || !tokenHeader.startsWith(AUTH_HEADER_PREFIX)) {
            return null;
        }
        final String token = tokenHeader.substring(AUTH_HEADER_PREFIX.length());

        Jws<Claims> claimsJws = tokenHandler.parseJwtToken(token);
        UUID userId = UUID.fromString(claimsJws.getBody().getSubject());
        List<String> roles = (List<String>)(claimsJws.getBody().get("rls", ArrayList.class));
        String userName = claimsJws.getBody().get("usr", String.class);

        User user = userService.findOrCreate(userId);

        user.setCurrentToken(token);
        return new JwtAuthentication(user, userName, new TreeSet<>(roles));
    }

    @RequiredArgsConstructor
    private static class JwtAuthentication implements Authentication {
        @Getter @Setter
        private boolean authenticated = true;
        private final User user;

        private final String userName;
        private final Set<String> roles;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return user;
        }

        @Override
        public Object getPrincipal() {
            return user;
        }

        @Override
        public String getName() {
            return userName;
        }
    }

}
