package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.web.output.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class From {
  private String email;
  private String name;
}
