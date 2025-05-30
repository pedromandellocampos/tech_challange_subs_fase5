package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
@Schema(
    description = "Data Transfer Object for resident login",
    title = "Resident Login DTO")
public class ResidentLoginDTO {
  @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Schema(
        description = "Email of the resident",
        example = "tex.mattina@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  String email;
  @NotBlank(message = "Password cannot be blank")
    @Schema(
        description = "Password for the resident account",
        example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
  String password;
}
