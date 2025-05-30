package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.model.Notification;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTOMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.input.NotificationMailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageInputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageOutputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MessageServiceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.NotificationMailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NotificationUseCase implements NotificationMailUseCaseInputPort {

  private MailMessageOutputOutputPort mailMessageOutputOutputPort;
  private ResidentUseCaseInputPort residentUseCaseInputPort;
  private NotificationMailPersistenceOutputPort notificationPersistenceOutputPort;
  private NotificationDTOMapper notificationDTOMapper;
  private MessageServiceOutputPort messageServiceOutputPort;

  @Override
  public void processNotification(MailDTO mailDTO) {
    String unity = mailDTO.getUnity();
    List<ResidentDTO> residents = residentUseCaseInputPort.getResidentByUnity(unity);
    if (residents != null && !residents.isEmpty()) {
      for (ResidentDTO resident : residents) {
        String email = resident.getEmail();
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setMessage("Pegue sua encomenda na portaria do seu predio");
        notification.setMailID(mailDTO.getId());
        notification.setMessageDeliveryTimestamp( LocalDateTime.now() );

        Notification notificationSent = notificationPersistenceOutputPort.save(notification);
        NotificationDTO notificationDTOToSend = notificationDTOMapper.toDTO(notificationSent);

        mailMessageOutputOutputPort.sendNotification(notificationDTOToSend);
        messageServiceOutputPort.sendMessageToUser(notificationDTOToSend);
      }
    }
  }


}
