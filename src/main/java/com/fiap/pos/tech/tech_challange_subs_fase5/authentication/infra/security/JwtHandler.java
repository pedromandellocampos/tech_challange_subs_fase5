package com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtHandler {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.issuer}")
  private String issuer;

  public String generateToken(EmployeeUserDetailDTO user){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
        .withIssuer(issuer)
        .withSubject(user.getEmail())
        .withExpiresAt(genExpirationDate())
        .withClaim("role", "ROLE_EMPLOYEE")
        .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String generateToken(ResidentUserDetailDTO user){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
        .withIssuer(issuer)
        .withSubject(user.getEmail())
        .withExpiresAt(genExpirationDate())
        .withClaim("role", "ROLE_RESIDENT")
        .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String validateToken(String token){
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
        .withIssuer(issuer)
        .build()
        .verify(token)
        .getSubject();
    } catch (JWTVerificationException exception){
      return "";
    }
  }


  public List<String> getRoles(String token){
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String role = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()
        .verify(token)
        .getClaim("role").asString();

      List<String> roles = Arrays.asList(role.split(","));

      System.out.println("Aqui dentro valida roles: " + role);
      return roles;
    } catch (JWTVerificationException exception){
      return List.of();
    }
  }

  private Instant genExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

}
