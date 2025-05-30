package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationDTOMapper {
  Notification toDomain(NotificationDTO notificationDTO);
  NotificationDTO toDTO(Notification notification);
}
