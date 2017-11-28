package de.yannickmortier.authserver.jwtsecurity;

import de.yannickmortier.authserver.domain.JwtKey;
import de.yannickmortier.authserver.service.JwtKeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultSigningKeyResolver implements SigningKeyResolver {

    private final JwtKeyService jwtKeyService;
    private Map<UUID, PublicKey> keyCache = new ConcurrentHashMap<>();

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        return resolveKeyFromHeader(header);
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, String plaintext) {
        return resolveKeyFromHeader(header);
    }

    private synchronized Key resolveKeyFromHeader(JwsHeader header) {
        String keyIdAsString = header.getKeyId();

        if (keyIdAsString == null) {
            log.warn("Signing key missing.", header.getKeyId());
            return null;
        }

        UUID keyId;
        try {
            keyId = UUID.fromString(keyIdAsString);
        } catch (IllegalArgumentException e) {
            log.warn("Got illegal key ID: {}", header.getKeyId());
            return null;
        }

        PublicKey key;
        if (!keyCache.containsKey(keyId)) {
            key = loadKeyFromService(keyId);
        } else {
            key = keyCache.get(keyId);
        }
        return key;
    }

    private synchronized PublicKey loadKeyFromService(UUID keyId) {
        JwtKey jwtKey = jwtKeyService.findById(keyId);
        if (jwtKey == null) {
            return null;
        }

        PublicKey key = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(jwtKey.getEncodedKey());
            key = kf.generatePublic(spec);
        }  catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // This should not happen, Algorithm isn't chosen dynamically, key comes from us
            log.warn("Exception happened: {}", e);
        }

        if (key != null) {
            keyCache.put(keyId, key);
        }
        return key;
    }

}
