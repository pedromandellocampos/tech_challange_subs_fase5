package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.web.output.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailTrapEmailRequest {
  private From from;
  private List<To> to;
  private String subject;
  private String text;
  private String category;
}
