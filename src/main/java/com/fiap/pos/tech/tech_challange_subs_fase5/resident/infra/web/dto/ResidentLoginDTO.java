package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
public class ResidentLoginDTO {
  @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
  String email;
  @NotBlank(message = "Password cannot be blank")
  String password;
}
