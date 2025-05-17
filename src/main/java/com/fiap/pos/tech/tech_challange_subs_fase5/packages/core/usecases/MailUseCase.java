package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MailUseCase implements MailUseCaseInputPort {

  MailPersistenceOutputPort mailPersistenceOutputPort;
  MailMapper mailMapper;

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
    return mailList.stream().map(mailMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public MailDTO registerMail(MailDTO mailDTO) {
    Mail mail =  mailMapper.toEntity(mailDTO);
    return mailMapper.toDto(mailPersistenceOutputPort.save(mail));
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
    mail.setReceivedByResident(true);
    return mailMapper.toDto(mailPersistenceOutputPort.save(mail));
  }
}
