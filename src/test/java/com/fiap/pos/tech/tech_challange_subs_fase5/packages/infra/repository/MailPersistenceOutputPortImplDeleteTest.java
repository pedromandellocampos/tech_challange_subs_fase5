package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({MailPersistenceOutputPortImpl.class, MailEntityMapperImpl.class})
class MailPersistenceOutputPortImplDeleteTest {

  @Autowired
  MailPersistenceOutputPortImpl persistenceOutputPort;

  @Autowired
  MailJPARepository mailJPARepository;

  @Test
  void deleteMail_success() {
    Mail mail = new Mail();
    mail.setUnity("103");
    Mail saved = persistenceOutputPort.save(mail);

    boolean deleted = persistenceOutputPort.delete(saved.getId());
    assertTrue(deleted);
    assertTrue(persistenceOutputPort.getMailById(saved.getId()).isEmpty());
  }

  // Testa deletar ID inexistente (deve retornar true, mas não lançar exceção)
  @Test
  void deleteMail_notFound() {
    boolean deleted = persistenceOutputPort.delete(9999L);
    assertTrue(deleted);
  }

}