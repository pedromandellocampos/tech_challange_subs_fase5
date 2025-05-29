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
class MailPersistenceOutputPortImplUnimplementedMethodsTeste {

  @Autowired
  MailPersistenceOutputPortImpl persistenceOutputPort;

  @Autowired
  MailJPARepository mailJPARepository;

  // Testa buscar por unidade inexistente

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