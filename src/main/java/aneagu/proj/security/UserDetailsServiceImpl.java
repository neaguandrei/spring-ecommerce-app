package aneagu.proj.security;

import aneagu.proj.models.domain.Customer;
import aneagu.proj.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (!optionalCustomer.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        Customer user = optionalCustomer.get();
        return new User(user.getEmail(), user.getPassword(), emptyList());
    }
}
