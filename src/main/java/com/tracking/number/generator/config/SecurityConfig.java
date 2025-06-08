package com.tracking.number.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/** The type Security config. */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Security filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        exchanges ->
                                exchanges
                                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                                        .permitAll()
                                        .pathMatchers("/actuator/**")
                                        .permitAll()
                                        .pathMatchers("/tracking/**")
                                        .permitAll()
                                        .anyExchange()
                                        .authenticated())
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }

    /**
     * User details service user details service.
     *
     * @param passwordEncoder the password encoder
     * @return the user details service
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
        UserDetails user =
                User.withUsername("admin")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build();
        return new MapReactiveUserDetailsService(user);
    }

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

