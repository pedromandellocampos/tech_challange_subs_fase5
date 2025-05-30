package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
    description = "Data Transfer Object for changing user password",
    title = "Change Password DTO")
public class ChangePasswordDTO {

  @NotBlank(message = "New password cannot be blank")
  @Size(min = 6, max = 20, message = "New password must be between 6 and 20 characters")
  @Schema(
      description = "The new password for the user",
      example = "newPassword123",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String newPassword;

}
