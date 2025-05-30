package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
    description = "Data Transfer Object for Employee Login",
    title = "Employee Login DTO")
public class EmployeeLoginDTO {
  @NotBlank(message = "Email cannot be blank")
    @Schema(
        description = "Email of the employee",
        example = "john@email.com")
  private String email;
  @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Password of the employee",
        example = "securePassword123")
  private String password;
}
