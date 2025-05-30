package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Schema(description = "Data Transfer Object for Employee Registration")
public class EmployeeDTORegister {


  @NotBlank(message = "Name is mandatory")
  @Schema(description = "Name of the employee", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @Email(message = "Email should be valid")
  @Schema(description = "Email of the employee", example = "john@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;
  @NotBlank
  @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
  @Schema(description = "Password for the employee account", example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;
  @NotBlank
  @Size(min = 10, max = 12, message = "Phone number must be between 10 and 12 characters")
  @Pattern(regexp = "\\d+", message = "Phone number must contain only numeric digits")
  @Schema(description = "Phone number of the employee", example = "1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
  private String phone;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of birth should be in the format dd/MM/yyyy")
  @Schema(description = "Date of birth of the employee", example = "01/01/1990", requiredMode = Schema.RequiredMode.REQUIRED)
  private String dateOfBirth;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of hire should be in the format dd/MM/yyyy")
  @Schema(description = "Hire date of the employee", example = "01/01/2020", requiredMode = Schema.RequiredMode.REQUIRED)
  private String hireDate;
  @NotNull
  @Schema(description = "Indicates if the employee is active", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  private boolean active;

}
