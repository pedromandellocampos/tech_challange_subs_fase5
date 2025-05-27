package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  Long residentConfirmedMailId;
  boolean receivedByEmail;



}
