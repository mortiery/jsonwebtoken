package de.yannickmortier.authserver.jwtsecurity;

import de.yannickmortier.authserver.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TokenHeaderAuthenticationService {
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private final TokenHandler tokenHandler;

    Authentication getAuthentication(HttpServletRequest request) {
        final String tokenHeader = request.getHeader(AUTH_HEADER_NAME);
        if (tokenHeader == null || !tokenHeader.startsWith(AUTH_HEADER_PREFIX)) {
            return null;
        }
        final String token = tokenHeader.substring(AUTH_HEADER_PREFIX.length());

        final User user = tokenHandler.parseUserFromToken(token);
        if (user != null) {
            return new JwtAuthentication(user);
        }

        return null;
    }

    @RequiredArgsConstructor
    private static class JwtAuthentication implements Authentication {
        @Getter @Setter
        private boolean authenticated = true;
        private final User user;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .collect(Collectors.toSet());
        }

        @Override
        public Object getCredentials() {
            return user.getPassword();
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
            return user.getUserName();
        }
    }

}
