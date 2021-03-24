package aneagu.proj.security;

import aneagu.proj.models.entity.UserEntity;
import aneagu.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optionalCustomer = userRepository.findByEmail(email);
        if (!optionalCustomer.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        UserEntity userEntity = optionalCustomer.get();
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), emptyList());
    }
}
