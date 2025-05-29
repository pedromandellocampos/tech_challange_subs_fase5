package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({MailPersistenceOutputPortImpl.class, MailEntityMapperImpl.class})
class MailPersistenceOutputPortImplFindMailByUnityTest {

  @Autowired
  MailPersistenceOutputPortImpl persistenceOutputPort;

  @Autowired
  MailJPARepository mailJPARepository;

  @Test
  void findMailsByUnity_success() {
    Mail mail = new Mail();
    mail.setUnity("104");
    persistenceOutputPort.save(mail);

    List<Mail> mails = persistenceOutputPort.findMailsByUnity("104");
    assertFalse(mails.isEmpty());
    assertEquals("104", mails.get(0).getUnity());
  }

  // Testa buscar por unidade inexistente
  @Test
  void findMailsByUnity_notFound() {
    List<Mail> mails = persistenceOutputPort.findMailsByUnity("unidade-inexistente");
    assertTrue(mails.isEmpty());
  }


}