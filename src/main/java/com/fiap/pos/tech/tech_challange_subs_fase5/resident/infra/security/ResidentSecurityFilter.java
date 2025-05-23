package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ResidentSecurityFilter extends OncePerRequestFilter {

  ResidentJwtHandler residentJwtHandler;
  ResidentAuthorizationService residentAuthorizationService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    if(request.getRequestURI().equals("/api/v1/residents/login")) {

      String token = this.recoverToken(request);
      if(token != null){
        String login = residentJwtHandler.validateToken(token);
        UserDetails userDetails = residentAuthorizationService.loadUserByUsername(login);

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
