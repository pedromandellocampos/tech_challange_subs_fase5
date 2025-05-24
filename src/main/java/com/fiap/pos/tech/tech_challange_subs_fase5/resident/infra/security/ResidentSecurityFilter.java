package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ResidentSecurityFilter extends OncePerRequestFilter {

  ResidentJwtHandler residentJwtHandler;
  AuthorizationService authorizationService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    if(request.getRequestURI().equals("/api/v1/residents/login")) {

      System.out.println("Inside ResidentSecurityFilter");

      String token = this.recoverToken(request);
      if(token != null){
        String login = residentJwtHandler.validateToken(token);
        System.out.println("Login: " + login);
        UserDetails userDetails = authorizationService.loadUserByUsername(login);
        System.out.println("Login: " + userDetails.getUsername());
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request){
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }

}
