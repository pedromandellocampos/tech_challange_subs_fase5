package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMailDTO {
  String description;
  String deliveryIssuerName;
  Long employeeRecipientId;
  Long residentRecipientId;
  String deliveryTimestamp;
  String unity;
}
