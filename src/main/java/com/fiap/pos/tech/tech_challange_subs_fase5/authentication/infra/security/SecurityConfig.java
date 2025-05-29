package com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain employeeSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
      .securityMatcher("/api/v1/**")
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/v1/employees/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/employees").permitAll()
        .requestMatchers("/api/v1/residents/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/residents").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/mail").hasRole("EMPLOYEE")
        .requestMatchers(HttpMethod.PUT, "/api/v1/mail").hasRole("EMPLOYEE")
        .anyRequest().authenticated()
      )
      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
