package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MailUseCase implements MailUseCaseInputPort {

  MailPersistenceOutputPort mailPersistenceOutputPort;
  MailMapper mailMapper;
  MailMessageOutputPort mailMessageOutputPort;

  @Override
  public List<MailDTO> listMailsByResidentId(Long residentId) {
    return List.of();
  }

  @Override
  public List<MailDTO> listMailsByEmployeeId(Long employeeId) {
    return List.of();
  }

  @Override
  public List<MailDTO> listMailsByDeliveryIssuerId(Long deliveryIssuerId) {
    return List.of();
  }

  @Override
  public List<MailDTO> listAllMails(int page, int size) {
    List<Mail> mailList = mailPersistenceOutputPort.listMails(page, size);
    return mailList.stream().map(mailMapper::toDto).toList();
  }

  @Override
  public MailDTO registerMail(MailDTO mailDTO) {

    Mail mail = mailPersistenceOutputPort.save(mailMapper.toEntity(mailDTO));
    MailDTO savedMailDTO = mailMapper.toDto(mail);
    mailMessageOutputPort.sendNotification(savedMailDTO);
    return savedMailDTO;
  }

  @Override
  public MailDTO updateMail(MailDTO mailDTO) {
    Mail mail =  mailMapper.toEntity(mailDTO);
    return mailMapper.toDto(mailPersistenceOutputPort.save(mail));
  }

  @Override
  public boolean deleteMail(Long id) {
    mailPersistenceOutputPort.delete(id);
    return true;
  }

  @Override
  public MailDTO receiveMail(MailDTO mailDTO) {
    Mail mail =  mailMapper.toEntity(mailDTO);
    Mail mailSaved = mailPersistenceOutputPort.getMailById(mail.getId())
        .orElseThrow(() -> new IllegalArgumentException("Mail not found"));

    mailSaved.setAcknowledgmentTimestamp(mail.getAcknowledgmentTimestamp());
    mailSaved.setResidentAcknowledgedById(mail.getResidentAcknowledgedById());
    mailSaved.setReceivedByResident(true);
    System.out.println(mailSaved.toString());
    return mailMapper.toDto(mailPersistenceOutputPort.save(mailSaved));
  }

  @Override
  public MailDTO getMailById(Long id) {
    Mail mail = mailPersistenceOutputPort.getMailById(id)
        .orElseThrow(() -> new IllegalArgumentException("Mail not found"));
    return mailMapper.toDto(mail);
  }

  @Override
  public MailDTO confirmNotification(MailDTO mailDTO) {

    Mail mail = mailMapper.toEntity(mailDTO);
    Mail mailSaved = mailPersistenceOutputPort.getMailById(mail.getId())
        .orElseThrow(() -> new IllegalArgumentException("Mail not found"));

    mailSaved.setResidentConfirmedMailId(mailDTO.getResidentAcknowledgedById());
    mailSaved.setReceivedByResident(true);
    return mailMapper.toDto(mailPersistenceOutputPort.save(mailSaved));

  }

  @Override
  public List<MailDTO> getMailByUnity(String unity) {
    List<Mail> mailList = mailPersistenceOutputPort.findMailsByUnity(unity);
    if (mailList.isEmpty()) {
      throw new IllegalArgumentException("No mails found for the specified unity");
    }
    return mailList.stream()
        .map(mailMapper::toDto)
        .toList();
  }
}
