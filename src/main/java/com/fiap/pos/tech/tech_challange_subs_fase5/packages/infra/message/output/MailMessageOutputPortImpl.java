package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.message.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class MailMessageOutputPortImpl implements MailMessageOutputPort {

  private KafkaTemplate<String, MailDTO> kafkaTemplate;

  @Override
  public void sendNotification(MailDTO mailDTO) {

    try {
      String json = new ObjectMapper().writeValueAsString(mailDTO);
      kafkaTemplate.send("tech_challange_subs_fase5-input", mailDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to send message to Kafka", e);
    }




  }

}
