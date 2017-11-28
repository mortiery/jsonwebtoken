package de.yannickmortier.productservice.service;

import de.yannickmortier.productservice.domain.User;
import de.yannickmortier.productservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findOrCreate(UUID authId) {
        User user = userRepository.findByRemoteId(authId);

        if (user != null) {
            return user;
        }
        User newUser = new User();
        newUser.setRemoteId(authId);
        userRepository.saveAndFlush(newUser);
        return newUser;
    }
}
