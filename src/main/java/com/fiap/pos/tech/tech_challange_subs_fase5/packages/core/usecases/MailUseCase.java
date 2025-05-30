package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MailUseCase implements MailUseCaseInputPort {

  private MailPersistenceOutputPort mailPersistenceOutputPort;
  private MailMapper mailMapper;
  private MailMessageOutputPort mailMessageOutputPort;
  private ResidentUseCaseInputPort residentUseCaseInputPort;

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
//    Mail mail = mailPersistenceOutputPort.getMailById(id)
//        .orElseThrow(() -> new IllegalArgumentException("Mail not found"));
    mailPersistenceOutputPort.delete(id);
    return true;
  }

  @Override
  public MailDTO receiveMail(MailDTO mailDTO) {
    Mail mail =  mailMapper.toEntity(mailDTO);
    Mail mailSaved = mailPersistenceOutputPort.getMailById(mail.getId())
        .orElseThrow(() -> new IllegalArgumentException("Mail not found"));

    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentById(mail.getResidentAcknowledgedById());


    if(!residentDTO.getApartment().equals(mailSaved.getUnity())){
      throw new IllegalArgumentException("Resident does not belong to the unity specified as a receiver for the mail");
    }

    mailSaved.setAcknowledgmentTimestamp(mail.getAcknowledgmentTimestamp());
    mailSaved.setResidentAcknowledgedById(mail.getResidentAcknowledgedById());
    mailSaved.setReceivedByResident(true);

    validateMail(mailSaved);
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
    }
    if (mail.getResidentRecipientId() != null){
      residentUseCaseInputPort.getResidentById(mail.getResidentRecipientId());
    }
    if(mail.getResidentConfirmedMailId() != null){
      residentUseCaseInputPort.getResidentById(mail.getResidentConfirmedMailId());
    }
  }
}
