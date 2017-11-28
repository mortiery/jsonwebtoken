package de.yannickmortier.authserver.jwtsecurity;

import de.yannickmortier.authserver.domain.JwtKey;
import de.yannickmortier.authserver.service.JwtKeyService;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
class KeyHolder {

    private final JwtKeyService jwtKeyService;

    private boolean initialized = false;
    private KeyPair keyPair;
    private UUID currentKeyId;

    synchronized PrivateKey getPrivateKey() {
        if (!initialized) {
            initialize();
        }
        return keyPair.getPrivate();
    }

    synchronized UUID getCurrentKeyId() {
        if (!initialized) {
            initialize();
        }
        return currentKeyId;
    }

    private synchronized void initialize() {
        keyPair = RsaProvider.generateKeyPair(1024);
        byte[] encodedKey = keyPair.getPublic().getEncoded();
        JwtKey jwtKey = new JwtKey(null, encodedKey);
        jwtKeyService.save(jwtKey);
        currentKeyId = jwtKey.getId();
        initialized = true;
    }
}
