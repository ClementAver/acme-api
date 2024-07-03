package com.acme.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.List;

// Enables any Java application to work with Spring Security in the Spring framework.
@Configuration
// Informs your Spring Boot application that it will be using Spring Security.
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SpringSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Used to configure the filter chain.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
         * HTTP requests are sent to authorizeHttpRequests() to restrict access based on roles,
         * then requestMatchers() matches the request to a specific pattern, which is either "ADMIN" (1) or "USER" (0).
         */
            http.csrf(AbstractHttpConfigurer::disable)  // Utilisation de AbstractHttpConfigurer pour désactiver CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())).authorizeHttpRequests(auth -> {
                // No Auth or Permissions on login (else can't be done...).
                auth.requestMatchers("/api/login").permitAll();
                // Must be at least a user to request the api.
                auth.requestMatchers("/api/**").hasRole("USER");
                // Must be admin to run POST or PUT method on employees (create or update).
                auth.requestMatchers(HttpMethod.POST, "/api/employee").hasRole("ADMIN");
                auth.requestMatchers(HttpMethod.PUT, "/api/employee").hasRole("ADMIN");
                // Ensures that all unauthenticated requests trigger a 401 error.
                auth.anyRequest().authenticated();
            }).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

    return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    // Used to generate a user and an admin for testing purpose.
//    //!\ Not for production. /!\\
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("USER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    // Password encryption.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Return an AuthenticationManager, able to manage an authentication source like userDetailsService
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
