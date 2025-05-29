package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDTO {

  @NotBlank(message = "Name is mandatory")
  private String name;
  @Email(message = "Email should be valid")
  private String email;
  @NotBlank
  @Size(min = 10, max = 12, message = "Phone number must be between 10 and 12 characters")
  private String phone;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of birth should be in the format dd/MM/yyyy")
  private String dateOfBirth;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of hire should be in the format dd/MM/yyyy")
  private String hireDate;
  @NotNull
  private boolean active;

}
