package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

  Long id;
  String description;

  String deliveryIssuerName;
  Long employeeRecipientId;
  Long residentRecipientId;
  String deliveryTimestamp;

  String unity;

  Long residentAcknowledgedById;
  String acknowledgmentTimestamp;

  boolean receivedByResident;

  Long residentConfirmedMailId;
  boolean receivedByEmail;

}
