package de.yannickmortier.cartservice.jwtsecurity;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenHeaderAuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Authentication jwtAuthentication;
        try {
            jwtAuthentication = authenticationService.getAuthentication(httpRequest);
        }
        catch (ExpiredJwtException e) {
            log.info("Expired token coming from {}: {} ", httpRequest.getRemoteAddr(), e.toString());
            ((HttpServletResponse)servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
            SecurityContextHolder.getContext().setAuthentication(null);
            return;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.warn("Invalid token coming from {}: {} ", httpRequest.getRemoteAddr(), e.toString());
            ((HttpServletResponse)servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
            SecurityContextHolder.getContext().setAuthentication(null);
            return;
        }
        log.info("User logged in: {} ", jwtAuthentication != null ? jwtAuthentication.getName() : "no user");
        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
        filterChain.doFilter(servletRequest, servletResponse);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
