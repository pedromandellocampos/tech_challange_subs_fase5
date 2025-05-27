package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({MailPersistenceOutputPortImpl.class, MailEntityMapperImpl.class})
class MailPersistenceOutputPortImplIT {

  @Autowired
  MailPersistenceOutputPortImpl persistenceOutputPort;

  @Autowired
  MailJPARepository mailJPARepository;

  @Test
  void save_and_getMailById_success() {
    Mail mail = new Mail();
    mail.setUnity("101");
    Mail saved = persistenceOutputPort.save(mail);

    Optional<Mail> found = persistenceOutputPort.getMailById(saved.getId());
    assertTrue(found.isPresent());
    assertEquals("101", found.get().getUnity());
  }

  @Test
  void listMails_pagination() {
    Mail mail1 = new Mail();
    mail1.setUnity("101");
    persistenceOutputPort.save(mail1);

    Mail mail2 = new Mail();
    mail2.setUnity("102");
    persistenceOutputPort.save(mail2);

    List<Mail> mails = persistenceOutputPort.listMails(0, 10);
    assertFalse(mails.isEmpty());
    assertTrue(mails.size() >= 2);
  }

  @Test
  void deleteMail_success() {
    Mail mail = new Mail();
    mail.setUnity("103");
    Mail saved = persistenceOutputPort.save(mail);

    boolean deleted = persistenceOutputPort.delete(saved.getId());
    assertTrue(deleted);
    assertTrue(persistenceOutputPort.getMailById(saved.getId()).isEmpty());
  }

  @Test
  void findMailsByUnity_success() {
    Mail mail = new Mail();
    mail.setUnity("104");
    persistenceOutputPort.save(mail);

    List<Mail> mails = persistenceOutputPort.findMailsByUnity("104");
    assertFalse(mails.isEmpty());
    assertEquals("104", mails.get(0).getUnity());
  }

  @Test
  void getMailById_notFound() {
    Optional<Mail> found = persistenceOutputPort.getMailById(9999L);
    assertTrue(found.isEmpty());
  }

  // Testa deletar ID inexistente (deve retornar true, mas não lançar exceção)
  @Test
  void deleteMail_notFound() {
    boolean deleted = persistenceOutputPort.delete(9999L);
    assertTrue(deleted);
  }

  // Testa buscar por unidade inexistente
  @Test
  void findMailsByUnity_notFound() {
    List<Mail> mails = persistenceOutputPort.findMailsByUnity("unidade-inexistente");
    assertTrue(mails.isEmpty());
  }

  @Test
  void listMailsByResidentId_returnsEmptyList() {
    List<Mail> mails = persistenceOutputPort.listMailsByResidentId(999L);
    assertTrue(mails.isEmpty());
  }

  @Test
  void listMailsByEmployeeId_returnsEmptyList() {
    List<Mail> mails = persistenceOutputPort.listMailsByEmployeeId(999L);
    assertTrue(mails.isEmpty());
  }

  @Test
  void listMailsByDeliveryIssuerId_returnsEmptyList() {
    List<Mail> mails = persistenceOutputPort.listMailsByDeliveryIssuerId(999L);
    assertTrue(mails.isEmpty());
  }
}