package aneagu.proj.security;

import aneagu.proj.models.domain.User;
import aneagu.proj.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationManagerImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Optional<User> optionalCustomer = userRepository.findByEmail(email);

        if (!optionalCustomer.isPresent()) {
            throw new BadCredentialsException(String.format("E-mail: %s doesn't exist!", email));
        }

        if (!passwordEncoder.matches(password, optionalCustomer.get().getPassword())) {
            throw new BadCredentialsException("Passwords don't match!");
        }

        return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());
    }
}
