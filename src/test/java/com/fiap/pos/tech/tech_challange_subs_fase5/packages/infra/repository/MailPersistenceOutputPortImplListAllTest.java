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
class MailPersistenceOutputPortImplListAllTest {

  @Autowired
  MailPersistenceOutputPortImpl persistenceOutputPort;

  @Autowired
  MailJPARepository mailJPARepository;

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

}