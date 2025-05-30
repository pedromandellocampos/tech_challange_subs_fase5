package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;

public interface MailMessageOutputOutputPort {
  void sendNotification(NotificationDTO notificationDTO);
}
