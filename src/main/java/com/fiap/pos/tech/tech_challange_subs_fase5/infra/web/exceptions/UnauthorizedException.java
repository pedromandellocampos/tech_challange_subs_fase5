// src/main/java/com/fiap/pos/tech/tech_challange_subs_fase5/shared/exception/UnauthorizedException.java
package com.fiap.pos.tech.tech_challange_subs_fase5.infra.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}