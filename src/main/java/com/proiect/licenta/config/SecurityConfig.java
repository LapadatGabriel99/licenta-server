package com.proiect.licenta.config;

import com.proiect.licenta.exception.AuthenticationEntryPointJwt;
import com.proiect.licenta.security.JwtTokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenVerifier authenticationJwtTokenFilter() {

        return new JwtTokenVerifier();
    }

    @Bean
    public AuthenticationEntryPointJwt authenticationEntryPointJwt() {

        return new AuthenticationEntryPointJwt();
    }
}
