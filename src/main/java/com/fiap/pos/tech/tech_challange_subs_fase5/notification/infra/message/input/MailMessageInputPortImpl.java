package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.message.input;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MailMessageInputPortImpl {


  @KafkaListener(topics = "tech_challange_subs_fase5-input", groupId = "notification-group")
  public void receiveMailMessage(String message) {
    // Aqui você pode processar a mensagem recebida do tópico Kafka
    System.out.println("Mensagem recebida no tópico mail-topic: " + message);
    // Lógica para enviar o e-mail pode ser implementada aqui
  }



}
