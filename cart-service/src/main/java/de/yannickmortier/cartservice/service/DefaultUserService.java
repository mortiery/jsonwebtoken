package de.yannickmortier.cartservice.service;

import de.yannickmortier.cartservice.domain.User;
import de.yannickmortier.cartservice.repository.UserRepository;
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

    @Override
    public User findOne(UUID userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

}
