package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class MailPersistenceOutputPortImpl implements MailPersistenceOutputPort {

  MailEntityMapper mailEntityMapper;
  MailJPARepository mailJPARepository;

  @Override
  public List<Mail> listMailsByResidentId(Long residentId) {
    return List.of();
  }

  @Override
  public List<Mail> listMailsByEmployeeId(Long employeeId) {
    return List.of();
  }

  @Override
  public List<Mail> listMailsByDeliveryIssuerId(Long deliveryIssuerId) {
    return List.of();
  }

  @Override
  public List<Mail> listMails(int page, int size) {
    Page<MailEntity> mailEntities = mailJPARepository.findAll(Pageable.ofSize(size).withPage(page));
    return mailEntities.stream()
        .map(mailEntityMapper::toDomain)
        .toList();
  }

  @Override
  public Mail save(Mail mail) {
    MailEntity mailEntity = mailEntityMapper.toEntity(mail);
    return mailEntityMapper.toDomain(mailJPARepository.save(mailEntity));
  }

  @Override
  public boolean delete(Long id) {
    mailJPARepository.deleteById(id);
    return true;
  }
}
