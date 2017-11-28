package de.yannickmortier.authserver.controllers;

import de.yannickmortier.authserver.dto.LoginTokenHolder;
import de.yannickmortier.authserver.dto.RefreshTokenHolder;
import de.yannickmortier.authserver.jwtsecurity.TokenHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class RefreshController {

    private final TokenHandler tokenHandler;

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<LoginTokenHolder> refresh(@Valid @RequestBody RefreshTokenHolder refreshTokenHolder, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String loginToken = tokenHandler.createTokenForRefreshToken(refreshTokenHolder.getRefreshToken());
            return ResponseEntity.ok(new LoginTokenHolder(loginToken));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
