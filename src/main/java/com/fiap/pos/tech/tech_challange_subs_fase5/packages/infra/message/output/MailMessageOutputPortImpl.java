package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.message.output;

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

  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void sendNotification(MailDTO mailDTO) {
    kafkaTemplate.send("tech_challange_subs_fase5", mailDTO.toString());
  }

}
