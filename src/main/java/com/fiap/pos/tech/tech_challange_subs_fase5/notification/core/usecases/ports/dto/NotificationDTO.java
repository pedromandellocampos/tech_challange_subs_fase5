package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
  Long id;
  String email;
  Long mailID;
  String message;
  LocalDateTime messageDeliveryTimestamp;
}
