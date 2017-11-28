package de.yannickmortier.authserver.controllers;

import de.yannickmortier.authserver.domain.User;
import de.yannickmortier.authserver.dto.LoginCredentials;
import de.yannickmortier.authserver.dto.TokenHolder;
import de.yannickmortier.authserver.jwtsecurity.TokenHandler;
import de.yannickmortier.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LoginController {

    private final UserService userService;
    private final TokenHandler tokenHandler;

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<TokenHolder> login(@Valid @RequestBody LoginCredentials loginCredentials,
                                             BindingResult result,
                                             HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User user =  userService.findByUserName(loginCredentials.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean passwordMatches = bCryptPasswordEncoder.matches(loginCredentials.getPassword(), user.getPassword());

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        final String idToken = tokenHandler.createTokenForUser(user);
        final String refreshToken = tokenHandler.createRefreshTokenForUser(user, request.getRemoteAddr());

        return ResponseEntity.ok(new TokenHolder(idToken, refreshToken));
    }

}
