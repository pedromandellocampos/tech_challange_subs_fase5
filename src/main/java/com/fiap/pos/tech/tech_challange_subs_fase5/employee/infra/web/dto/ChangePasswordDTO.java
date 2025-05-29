package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDTO {

  @NotBlank(message = "New password cannot be blank")
  @Size(min = 6, max = 20, message = "New password must be between 6 and 20 characters")
  private String newPassword;

}
