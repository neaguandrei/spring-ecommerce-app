package com.fmi.security;

import com.fmi.security.model.User;
import com.fmi.security.model.MyUserDetails;
import com.fmi.security.service.UserGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserGatewayService userGatewayService;

    @Override
    public MyUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userGatewayService.getUserByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(email);
        }

        return new MyUserDetails(user);
    }
}
