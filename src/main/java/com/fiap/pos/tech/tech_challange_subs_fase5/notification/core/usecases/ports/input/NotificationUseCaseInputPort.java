package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.input;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;

public interface NotificationUseCaseInputPort {
  void processNotification(MailDTO mailDTO);
}
