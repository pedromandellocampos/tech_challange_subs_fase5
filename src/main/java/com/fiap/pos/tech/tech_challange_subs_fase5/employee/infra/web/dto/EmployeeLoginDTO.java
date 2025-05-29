package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeLoginDTO {
  @NotBlank(message = "Email cannot be blank")
  String email;
  @NotBlank(message = "Password cannot be blank")
  String password;
}
