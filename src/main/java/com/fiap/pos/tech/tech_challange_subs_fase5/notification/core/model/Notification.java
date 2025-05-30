package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
  private Long id;
  private String email;
  private Long mailID;
  private String message;
  private LocalDateTime messageDeliveryTimestamp;
}
