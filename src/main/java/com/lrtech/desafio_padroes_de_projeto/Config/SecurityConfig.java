package com.lrtech.desafio_padroes_de_projeto.Config;

import com.lrtech.desafio_padroes_de_projeto.Services.Auth.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws
            Exception {

        http.securityMatcher(PathRequest.toH2Console()).csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions ->
                        frameOptions.disable()));
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //clientes
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/client").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/client/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/client/*").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
