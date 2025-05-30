package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationEntityMapper {
  NotificationEntity toEntity(Notification notificationEntityDTO);
  Notification toDTO(NotificationEntity notificationEntity);
}
