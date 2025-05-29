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
class MailPersistenceOutputPortImplSaveTest {

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
}

