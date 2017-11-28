package de.yannickmortier.authserver.controllers;

import de.yannickmortier.authserver.domain.JwtKey;
import de.yannickmortier.authserver.dto.KeyDto;
import de.yannickmortier.authserver.service.JwtKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/keys")
@RequiredArgsConstructor( onConstructor = @__({@Autowired}))
public class KeyController {
    private final JwtKeyService jwtKeyService;

    @RequestMapping( value = "{id}", method = RequestMethod.GET )
    public ResponseEntity<KeyDto> getJwtKey(@PathVariable UUID id) {

        JwtKey key = jwtKeyService.findById(id);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }

        KeyDto keyDto = new KeyDto();
        String base64EncodedKey = new String(Base64.getEncoder().encode(key.getEncodedKey()));
        keyDto.setKeyBase64(base64EncodedKey);

        return ResponseEntity.ok().body(keyDto);
    }
}
