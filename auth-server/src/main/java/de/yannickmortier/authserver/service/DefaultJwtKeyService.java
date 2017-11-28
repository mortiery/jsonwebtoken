package de.yannickmortier.authserver.service;

import de.yannickmortier.authserver.domain.JwtKey;
import de.yannickmortier.authserver.repository.JwtKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultJwtKeyService implements  JwtKeyService {

    private final JwtKeyRepository jwtKeyRepository;

    @Override
    public JwtKey findById(UUID id) {
        return jwtKeyRepository.findOne(id);
    }

    @Override
    public void save(JwtKey jwtKey) {
        jwtKeyRepository.save(jwtKey);
    }
}
