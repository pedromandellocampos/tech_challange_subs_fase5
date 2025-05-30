package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    description = "Data Transfer Object for updating resident information",
    title = "Resident Update DTO")
public class ResidentUpdateDTO {

  @NotBlank(message = "Name cannot be blank")
  @Schema(
      description = "Name of the resident",
      example = "Tex Mattina",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  @Schema(
      description = "Email of the resident",
      example = "tex.mattina@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;
  @NotBlank(message = "Phone number cannot be blank")
  @Size(min = 10, max = 12, message = "Phone number must be between 10 and 12 characters")
  @Pattern(regexp = "\\d+", message = "Phone number must contain only numeric digits")
  @Schema(
      description = "Phone number of the resident",
      example = "1234567890",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String phone;
  @NotBlank(message = "Apartment cannot be blank")
  @Pattern(regexp = "\\d+", message = "Apartment number must contain only numeric digits")
  @Schema(
      description = "Apartment number of the resident",
      example = "101",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String apartment;
  @NotBlank(message = "Birth date cannot be blank")
  @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Birth date should be in the format dd/MM/yyyy")
  @Schema(
      description = "Date of birth of the resident in the format dd/MM/yyyy",
      example = "15/10/1990",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String birthDate;
  @NotNull(message = "Active status cannot be null")
  @Schema(
      description = "Indicates if the resident is active",
      example = "true",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private boolean active;

}
