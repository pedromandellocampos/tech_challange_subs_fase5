package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResidentLoginDTO {
  String email;
  String password;
}
