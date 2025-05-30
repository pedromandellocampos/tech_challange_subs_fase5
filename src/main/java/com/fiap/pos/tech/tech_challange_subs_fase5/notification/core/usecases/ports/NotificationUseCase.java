package com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.input.NotificationUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageInputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MailMessageOutputOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificationUseCase implements NotificationUseCaseInputPort {

  private MailMessageOutputOutputPort mailMessageOutputOutputPort;
  private MailMessageInputOutputPort mailMessageInputOutputPort;
  private NotificationUseCaseInputPort notificationUseCaseInputPort;
  private ResidentUseCaseInputPort residentUseCaseInputPort;

  @Override
  public void processNotification(MailDTO mailDTO) {

    String unity = mailDTO.getUnity();
    List<ResidentDTO> residents = residentUseCaseInputPort.getResidentByUnity(unity);
    if (residents != null && !residents.isEmpty()) {
      for (ResidentDTO resident : residents) {
        String email = resident.getEmail();
        mailMessageOutputOutputPort.sendNotification(mailDTO);
      }
    } else {
      System.out.println("No residents found for the specified unity: " + unity);
    }



  }


}
