package org.example.eventorganiser.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF for testing and APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
