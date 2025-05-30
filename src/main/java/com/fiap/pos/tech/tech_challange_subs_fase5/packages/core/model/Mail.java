package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Mail {

  private Long id;
  private String description;

  private String deliveryIssuerName;
  private Long employeeRecipientId;
  private Long residentRecipientId;
  private LocalDate deliveryTimestamp;

  private String unity;

  private Long residentAcknowledgedById;
  private LocalDate acknowledgmentTimestamp;

  private boolean receivedByResident;

  private Long residentConfirmedMailId;
  private boolean receivedByEmail;

}
