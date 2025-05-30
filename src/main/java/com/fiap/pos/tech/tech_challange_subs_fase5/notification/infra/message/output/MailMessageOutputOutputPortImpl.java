package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.message.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model.Notification;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageOutputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class MailMessageOutputOutputPortImpl implements MailMessageOutputOutputPort {

  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void sendNotification(NotificationDTO notificationDTO) {
    kafkaTemplate.send("tech_challange_subs_fase5-output", notificationDTO.toString());
  }
}
