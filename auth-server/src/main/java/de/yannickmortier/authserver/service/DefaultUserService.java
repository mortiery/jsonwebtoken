package de.yannickmortier.authserver.service;

import de.yannickmortier.authserver.domain.User;
import de.yannickmortier.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    @Override
    public User findById(UUID id) {
        return this.userRepository.findOne(id);
    }
}
