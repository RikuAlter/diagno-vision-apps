package com.diagno.vision.webapps.diagnovision.helper;

import com.diagno.vision.webapps.diagnovision.dto.User;
import com.diagno.vision.webapps.diagnovision.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(final User user) {
        final String methodName = "UserHelper.registerUser";

        User savedUser = null;
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try{
            savedUser = userRepository.save(user);
            return savedUser.getId() > 0;
        } catch(Exception e){
            log.error("{}: Failed to save user to database! Exception: {}", methodName, e.getMessage());
            return false;
        }
    }

    public User fetchUser(final Authentication authentication) {
        final Optional<User> user = userRepository.findByEmail(authentication.getName());
        return user.orElse(null);
    }

}
