package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTOToReturn {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private LocalDate dateOfBirth;
  private LocalDate hireDate;
  private boolean active;

}
