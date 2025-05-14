package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model;

import lombok.Data;

import java.time.LocalDate;

@Data
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
