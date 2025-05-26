package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output;


import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Notification;

import java.util.List;

public interface NotificationPersistenceOutputPort {

  Notification save(Notification notification);
  boolean delete(Notification notification);
  List<Notification> listAll(int page, int size);
  Notification findById(Long id);
  Notification findByMessage(String message);
  Notification findByType(String type);
  Notification findByConfirmed(boolean confirmed);

}
