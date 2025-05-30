package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model.Notification;

import java.util.List;

public interface NotificationMailPersistenceOutputPort {
  Notification save(Notification notificationDTO);
  List<Notification> listAllNotifications();
}
