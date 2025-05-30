package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.configuration;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.NotificationUseCase;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTOMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.input.NotificationMailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageInputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageOutputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MessageServiceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.NotificationMailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.repository.NotificationMailPersistenceOutputPortImpl;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

  @Bean
  public NotificationMailUseCaseInputPort notificationMailUseCaseInputPort(NotificationMailPersistenceOutputPort notificationMailPersistenceOutputPort, MailMessageOutputOutputPort mailMessageOutputOutputPort, ResidentUseCaseInputPort residentUseCaseInputPort, NotificationDTOMapper notificationDTOMapper, MessageServiceOutputPort messageServiceOutputPort) {
    return new NotificationUseCase(
      mailMessageOutputOutputPort,
      residentUseCaseInputPort,
      notificationMailPersistenceOutputPort,
      notificationDTOMapper, messageServiceOutputPort);
  }



}
