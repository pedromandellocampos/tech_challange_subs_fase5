package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;

import java.util.List;

public interface MailPersistenceOutputPort {

  List<Mail> listMailsByResidentId(Long residentId);
  List<Mail> listMailsByEmployeeId(Long employeeId);
  List<Mail> listMailsByDeliveryIssuerId(Long deliveryIssuerId);
  List<Mail> listMails(int page, int size);
  Mail save(Mail mail);
  boolean delete(Long id);

}
