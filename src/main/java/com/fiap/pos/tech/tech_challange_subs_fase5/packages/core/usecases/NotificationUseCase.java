package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.NotificationUseCaseInputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationUseCase implements NotificationUseCaseInputPort {

  private NotificationUseCaseInputPort notificationUseCaseInputPort;

  @Override
  public void sendNotification(MailDTO mailDTO) {

  }

}
