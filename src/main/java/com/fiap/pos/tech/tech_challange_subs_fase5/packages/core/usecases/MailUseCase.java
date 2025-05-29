package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MailUseCase implements MailUseCaseInputPort {

  MailPersistenceOutputPort mailPersistenceOutputPort;
  MailMapper mailMapper;
  MailMessageOutputPort mailMessageOutputPort;
  ResidentUseCaseInputPort residentUseCaseInputPort;

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
    validateMail(mailMapper.toEntity(mailDTO));
    Mail mail = mailPersistenceOutputPort.save(mailMapper.toEntity(mailDTO));
    MailDTO savedMailDTO = mailMapper.toDto(mail);
    mailMessageOutputPort.sendNotification(savedMailDTO);
    return savedMailDTO;
  }

  @Override
  public MailDTO updateMail(MailDTO mailDTO) {
    Mail mail =  mailMapper.toEntity(mailDTO);
    validateMail(mail);
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

    System.out.println("Mail received: " + mailSaved);
    validateMail(mailSaved);
    System.out.println("Mail received2: " + mailSaved);
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

    residentUseCaseInputPort.getResidentById(mail.getResidentConfirmedMailId());
    mailSaved.setResidentConfirmedMailId(mailDTO.getResidentConfirmedMailId());
    mailSaved.setReceivedByEmail(true);
    validateMail(mailSaved);
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

  public void validateMail(Mail mail){
    if (mail.getResidentAcknowledgedById() != null) {
      residentUseCaseInputPort.getResidentById(mail.getResidentAcknowledgedById());
      System.out.println("AQUI1");
    }
    if (mail.getResidentRecipientId() != null){
      System.out.println("AQUI2.1 -> " + mail.getResidentRecipientId());
      residentUseCaseInputPort.getResidentById(mail.getResidentRecipientId());
      System.out.println("AQUI2");
    }
    if(mail.getResidentConfirmedMailId() != null){
      System.out.println("AQUI3.1 -> " + mail.getResidentConfirmedMailId());
      residentUseCaseInputPort.getResidentById(mail.getResidentConfirmedMailId());
      System.out.println("AQUI3");
    }
  }
}
