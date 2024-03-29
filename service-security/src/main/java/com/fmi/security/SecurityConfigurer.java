package com.fmi.security;

import com.fmi.security.config.SecurityConfigurationProperties;
import com.fmi.security.service.CookieHandler;
import com.fmi.security.service.JwtHandler;
import com.fmi.security.service.UserGatewayService;
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
                    "/api/order-details/*",
                    "/api/payments/users/*",
                    "/api/users/*",
                    "/api/carts/final/*"
            };

    private final AuthenticationManager authenticationManager;

    private final DaoAuthenticationProvider authProvider;

    private final SecurityConfigurationProperties securityConfigurationProperties;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserGatewayService userGatewayService;

    private final CookieHandler cookieHandler;

    private final JwtHandler jwtHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(authenticationManager, userDetailsService, userGatewayService, cookieHandler, jwtHandler);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");

        final JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager, securityConfigurationProperties);

        if (securityConfigurationProperties.isEnabled()) {
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(AUTHORIZED_ENDPOINTS).authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(jwtAuthorizationFilter)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/api/auth/logout"))
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
