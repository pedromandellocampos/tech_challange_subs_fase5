package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input;


import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;

import java.util.List;

public interface MailUseCaseInputPort {

  List<MailDTO> listMailsByResidentId(Long residentId);
  List<MailDTO> listMailsByEmployeeId(Long employeeId);
  List<MailDTO> listMailsByDeliveryIssuerId(Long deliveryIssuerId);
  List<MailDTO> listAllMails(int page, int size);
  MailDTO registerMail(MailDTO mailDTO);
  MailDTO updateMail(MailDTO mailDTO);
  boolean deleteMail(Long id);
  MailDTO receiveMail(MailDTO mailDTO);
  MailDTO getMailById(Long id);
  MailDTO confirmNotification(MailDTO mailDTO);
  List<MailDTO> getMailByUnity(String unity);
}
