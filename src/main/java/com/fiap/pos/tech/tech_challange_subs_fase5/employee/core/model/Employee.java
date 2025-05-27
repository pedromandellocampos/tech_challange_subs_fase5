package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Employee {

  private Long id;
  private String name;
  private String email;
  private String password;
  private String phone;
  private LocalDate dateOfBirth;
  private LocalDate hireDate;
  private boolean active;

}
