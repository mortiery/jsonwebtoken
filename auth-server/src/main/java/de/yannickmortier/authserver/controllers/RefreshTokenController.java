package de.yannickmortier.authserver.controllers;

import de.yannickmortier.authserver.domain.RefreshToken;
import de.yannickmortier.authserver.domain.User;
import de.yannickmortier.authserver.dto.RefreshTokenDto;
import de.yannickmortier.authserver.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<RefreshTokenDto>> getAllSessions(@AuthenticationPrincipal User user) {
        List<RefreshToken> refreshTokens = refreshTokenService.getRefreshTokensForUser(user);
        List<RefreshTokenDto> dtos = refreshTokens.stream().map(refreshToken -> {
            RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
            BeanUtils.copyProperties(refreshToken, refreshTokenDto);
            refreshTokenDto.setUserId(user.getId());
            return refreshTokenDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRefreshToken(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        try {
            refreshTokenService.deleteTokenForUser(user, id);
        } catch (RefreshTokenService.TokenNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
