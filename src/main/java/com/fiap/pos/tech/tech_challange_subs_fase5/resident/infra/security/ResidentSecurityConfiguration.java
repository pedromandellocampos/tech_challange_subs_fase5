package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
public class ResidentSecurityConfiguration {

  ResidentSecurityFilter employeeSecurityFilter;

  @Bean
  public SecurityFilterChain residentSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
      .securityMatcher("/api/v1/residents/**")
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/v1/residents/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/residents").permitAll() // Cadastro de novo residente
      )
      .build();
  }


}
