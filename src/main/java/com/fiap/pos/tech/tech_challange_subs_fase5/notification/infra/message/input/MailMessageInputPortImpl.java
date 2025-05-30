package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.message.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.input.NotificationMailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class MailMessageInputPortImpl {

  private NotificationMailUseCaseInputPort notificationMailUseCaseInputPort;
  private ObjectMapper mapper = new ObjectMapper();

  @KafkaListener(topics = "tech_challange_subs_fase5-input", groupId = "notification-group", containerFactory = "mailKafkaListenerContainerFactory")
  public void receiveMailMessage(MailDTO mailDTO) throws Exception {

    notificationMailUseCaseInputPort.processNotification(mailDTO);
  }



}
