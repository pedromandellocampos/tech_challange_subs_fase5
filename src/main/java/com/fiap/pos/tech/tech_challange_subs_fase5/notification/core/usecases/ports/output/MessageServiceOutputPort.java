package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTO;

public interface MessageServiceOutputPort {
  void sendMessageToUser(NotificationDTO message);
}
