package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

  private Long id;
  private String description;

  private String deliveryIssuerName;
  private Long employeeRecipientId;
  private Long residentRecipientId;
  private String deliveryTimestamp;

  private String unity;

  private Long residentAcknowledgedById;
  private String acknowledgmentTimestamp;

  private boolean receivedByResident;

  private Long residentConfirmedMailId;
  private boolean receivedByEmail;

}
