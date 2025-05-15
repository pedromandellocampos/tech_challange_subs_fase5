package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDTOToReturn {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String apartment;
  private LocalDate birthDate;
  private boolean active;
}
