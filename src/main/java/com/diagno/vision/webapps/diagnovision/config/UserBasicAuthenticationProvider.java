package com.diagno.vision.webapps.diagnovision.config;

import com.diagno.vision.webapps.diagnovision.dto.User;
import com.diagno.vision.webapps.diagnovision.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Component
public class UserBasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userEmail = authentication.getName();
        final String userPassword = authentication.getCredentials().toString();

        final Optional<User> user = userRepository.findByEmail(userEmail);

        if(user.isPresent() && passwordEncoder.matches(userPassword, user.get().getPassword()))
            return new UsernamePasswordAuthenticationToken(userEmail,
                                                           userPassword,
                                                           Set.of(new SimpleGrantedAuthority(user.get().getRole().name())));
        else
            throw new BadCredentialsException("Invalid user credentials!");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
