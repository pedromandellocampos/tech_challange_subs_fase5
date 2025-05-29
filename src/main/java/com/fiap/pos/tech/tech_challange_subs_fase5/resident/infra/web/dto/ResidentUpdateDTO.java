package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentUpdateDTO {

  @NotBlank(message = "Name cannot be blank")
  private String name;
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  private String email;
  @NotBlank(message = "Phone number cannot be blank")
  private String phone;
  @NotBlank(message = "Apartment cannot be blank")
  private String apartment;
  @NotBlank(message = "Birth date cannot be blank")
  @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Birth date should be in the format dd/MM/yyyy")
  private String birthDate;
  @NotNull(message = "Active status cannot be null")
  private boolean active;

}
