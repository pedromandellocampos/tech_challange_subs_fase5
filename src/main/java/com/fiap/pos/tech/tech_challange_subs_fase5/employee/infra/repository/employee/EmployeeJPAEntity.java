package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
@Valid
public class EmployeeJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(unique = true, nullable = false)
  private String email;
  private String password;
  @Column(unique = true, nullable = false)
  private String phone;
  private LocalDate dateOfBirth;
  private LocalDate hireDate;
  private boolean active;

}
