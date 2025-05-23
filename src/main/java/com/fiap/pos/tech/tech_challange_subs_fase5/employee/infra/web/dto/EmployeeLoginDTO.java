package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeLoginDTO {
  String email;
  String password;
}
