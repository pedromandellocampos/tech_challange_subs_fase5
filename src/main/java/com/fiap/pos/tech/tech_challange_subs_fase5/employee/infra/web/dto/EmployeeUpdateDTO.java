package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

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
    description = "DTO for updating employee information",
    title = "Employee Update DTO")
public class EmployeeUpdateDTO {

  @NotBlank(message = "Name is mandatory")
  @Schema(
      description = "The name of the employee",
      example = "John Doe",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;
  @Email(message = "Email should be valid")
  @Schema(description = "The email of the employee", example = "john@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;
  @NotBlank
  @Size(min = 10, max = 12, message = "Phone number must be between 10 and 12 characters")
  @Pattern(regexp = "\\d+", message = "Phone number must contain only numeric digits")
  @Schema(description = "The phone number of the employee", example = "1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
  private String phone;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of birth should be in the format dd/MM/yyyy")
  @Schema(description = "The date of birth of the employee", example = "01/01/1990", requiredMode = Schema.RequiredMode.REQUIRED)
  private String dateOfBirth;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of hire should be in the format dd/MM/yyyy")
  @Schema(description = "The date of hire of the employee", example = "01/01/2020", requiredMode = Schema.RequiredMode.REQUIRED)
  private String hireDate;
  @NotNull
  @Schema(description = "Indicates if the employee is active", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  boolean active;

}
