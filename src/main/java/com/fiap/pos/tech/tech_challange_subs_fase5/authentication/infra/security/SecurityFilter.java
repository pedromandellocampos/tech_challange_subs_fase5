package com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  JwtHandler jwtHandler;
  UserDetailsService authorizationService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String token = this.recoverToken(request);
    if(token != null){
      System.out.println("Aqui dentro valida token: " + token);
      String login = jwtHandler.validateToken(token);
      logger.info("Aqui dentro valida login: " + login);
      UserDetails userDetails = authorizationService.loadUserByUsername(login);
      System.out.println("Aqui dentro valida userDetails: " + userDetails.getUsername());

      var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    System.out.println("a1ui12322");

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request){
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }

}
