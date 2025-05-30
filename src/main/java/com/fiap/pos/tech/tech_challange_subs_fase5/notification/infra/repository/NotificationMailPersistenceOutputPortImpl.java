package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model.Notification;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.NotificationMailPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@AllArgsConstructor
public class NotificationMailPersistenceOutputPortImpl implements NotificationMailPersistenceOutputPort {

  private NotificationEntityMapper notificationEntityMapper;
  private NotificationEntityJPARepository notificationEntityJPARepository;

  @Override
  public Notification save(Notification notificationDTO) {

    NotificationEntity notificationEntity = notificationEntityMapper.toEntity(notificationDTO);
   return notificationEntityMapper.toDTO(notificationEntityJPARepository.save(notificationEntity));
  }

  @Override
  public List<Notification> listAllNotifications() {
    return notificationEntityJPARepository.findAll().stream().map(notificationEntityMapper::toDTO).collect(Collectors.toList());
  }


}
