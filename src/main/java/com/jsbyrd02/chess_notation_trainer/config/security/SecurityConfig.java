package com.jsbyrd02.chess_notation_trainer.config.security;

import com.jsbyrd02.chess_notation_trainer.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authorizeHttpRequests(configurer ->
                configurer
                        // Allow access to /api/users/login and /api/users/create without authentication
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/game/**").permitAll()

                        // Require authentication for all other api endpoints
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/mm-analytics/**").authenticated()
        );

        // Enable Basic Auth with real DB users
        http.authenticationManager(authenticationManager);

        // Use HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        // Disable Cross Site Request Forgery (CSRF) for API use
        http.csrf(AbstractHttpConfigurer::disable);

        // Enable CORS
        http.cors(Customizer.withDefaults());

        return http.build();
    }
}
