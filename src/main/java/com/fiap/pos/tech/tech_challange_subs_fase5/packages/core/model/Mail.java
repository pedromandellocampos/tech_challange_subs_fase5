package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Mail {

  Long id;
  String description;

  String deliveryIssuerName;
  Long employeeRecipientId;
  Long residentRecipientId;
  LocalDate deliveryTimestamp;

  String unity;

  Long residentAcknowledgedById;
  LocalDate acknowledgmentTimestamp;

  boolean receivedByResident;

}
