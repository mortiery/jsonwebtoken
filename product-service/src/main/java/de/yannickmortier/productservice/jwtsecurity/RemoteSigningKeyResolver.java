package de.yannickmortier.productservice.jwtsecurity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.yannickmortier.productservice.dto.KeyDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RemoteSigningKeyResolver implements SigningKeyResolver {

    private LoadingCache<UUID, PublicKey> keyCache;

    public RemoteSigningKeyResolver() {
        this.keyCache = CacheBuilder.newBuilder()
                .maximumSize(1024)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<UUID, PublicKey>() {
                    @Override
                    public PublicKey load(UUID key) throws Exception {
                        RestTemplate restTemplate = new RestTemplate();
                        final String uri = "http://localhost:8080/keys/" + key.toString();
                        try {
                            KeyDto keyDto = restTemplate.getForObject(uri, KeyDto.class);
                            return convertEncodedKeyToKey(keyDto.getKeyBase64());
                        } catch (HttpStatusCodeException exception) {
                            log.error("Error fetching key: {}", exception.getStatusCode());
                            return null;
                        }
                    }

                    private PublicKey convertEncodedKeyToKey(String encodedKey) {
                        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                        PublicKey publicKey = null;
                        try {
                            KeyFactory kf = KeyFactory.getInstance("RSA");
                            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
                            publicKey = kf.generatePublic(spec);
                        } catch (NoSuchAlgorithmException e) {
                            log.error("Algorithm to create key not found!");
                            e.printStackTrace();
                        } catch (InvalidKeySpecException e) {
                            log.error("Invalid key received from auth server!");
                            e.printStackTrace();
                        }
                        return publicKey;
                    }
                });
    }

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
            log.warn("Signing key missing from token.", header.getKeyId());
            return null;
        }

        try {
            return keyCache.get(UUID.fromString(keyIdAsString));
        } catch (IllegalArgumentException e) {
            log.warn("Got illegal key ID: {}", header.getKeyId());
        } catch (ExecutionException e) {
            log.error("Excdeption during key fetching: {}", header.getKeyId());
            e.printStackTrace();
        }
        return null;

    }

}
