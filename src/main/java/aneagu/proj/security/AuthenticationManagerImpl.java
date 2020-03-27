package aneagu.proj.security;

import aneagu.proj.models.domain.Customer;
import aneagu.proj.repository.CustomerRepository;
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

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationManagerImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if (!optionalCustomer.isPresent()) {
            throw new BadCredentialsException(String.format("E-mail: %s doesn't exist!", email));
        }

        if (!passwordEncoder.matches(password, optionalCustomer.get().getPassword())) {
            throw new BadCredentialsException("Passwords don't match!");
        }

        return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());
    }
}
