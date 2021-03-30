package com.fmi.security;

import com.fmi.security.config.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final String[] AUTHORIZED_ENDPOINTS =
            new String[]{
                    "/order-details/*",
                    "/payments/*",
                    "/payments/users/*",
                    "/users/*"
            };

    private final AuthenticationManager authenticationManager;

    private final DaoAuthenticationProvider authProvider;

    private final SecurityConfigurationProperties securityConfigurationProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (securityConfigurationProperties.isEnabled()) {
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(AUTHORIZED_ENDPOINTS).authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, securityConfigurationProperties))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, securityConfigurationProperties))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .headers().frameOptions().disable()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint());
        } else {
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                    .headers().frameOptions().disable();
        }
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new SecurityAuthenticationEntryPoint();
    }

}
