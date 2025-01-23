package com.example.belajarRestApi.security;

import com.example.belajarRestApi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;


@Configuration
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtRequestFilter;
    private final UserService userService;

    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtRequestFilter = jwtAuthenticationFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer->corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration corsConfiguration=new CorsConfiguration();
                        corsConfiguration.setAllowCredentials(true);// allows taking authentication with credentials
                        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));// allowing all HTTP methods GET,POST,PUT etc, can configure on your need
                        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));// allowing all the request headers, can configure according to your need, which headers to allow
                        corsConfiguration.setMaxAge(Duration.ofMinutes(5L)); // setting the max time 5m till which the allowed origin will not make a pre-flight request again to check if the CORS is allowed on not
                        return corsConfiguration;
                    }
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/addresses/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/addresses/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/addresses/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/addresses/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/transactions/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/transactions/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::loadUserByUsername;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
