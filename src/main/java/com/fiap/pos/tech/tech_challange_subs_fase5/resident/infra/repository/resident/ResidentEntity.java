package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Entity
@Table(name = "resident")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class ResidentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(unique = true)
  private String email;
  private String password;
  private String phone;
  private String apartment;
  private LocalDate birthDate;
  private boolean active;
}
