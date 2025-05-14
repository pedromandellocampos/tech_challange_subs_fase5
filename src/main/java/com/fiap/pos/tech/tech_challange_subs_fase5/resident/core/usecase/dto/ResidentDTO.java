package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDTO {

  private Long id;
  private String name;
  private String email;
  private String password;
  private String phone;
  private String apartment;
  private LocalDate birthDate;
  private boolean active;

}
