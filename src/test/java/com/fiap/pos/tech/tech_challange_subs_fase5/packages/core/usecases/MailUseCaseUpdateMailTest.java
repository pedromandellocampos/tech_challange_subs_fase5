// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/packages/core/usecases/MailUseCaseUpdateMailTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailUseCaseUpdateMailTest {

  private MailPersistenceOutputPort persistencePort;
  private MailMapper mapper;
  private MailMessageOutputPort messageOutputPort;
  private MailUseCase useCase;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @BeforeEach
  void setUp() {
    persistencePort = mock(MailPersistenceOutputPort.class);
    mapper = mock(MailMapper.class);
    messageOutputPort = mock(MailMessageOutputPort.class);
    useCase = new MailUseCase(persistencePort, mapper, messageOutputPort);
  }

  @Test
  void updateMail_success() {
    MailDTO inputDTO = new MailDTO(
      1L,
      "Pacote Atualizado",
      "Entregador X",
      10L,
      100L,
      LocalDate.now().format(formatter),
      "101",
      null,
      null,
      false,
      null,
      false
    );

    Mail mailEntity = new Mail(
      1L,
      "Pacote Atualizado",
      "Entregador X",
      10L,
      100L,
      LocalDate.now(),
      "101",
      null,
      null,
      false,
      null,
      false
    );

    Mail savedMail = new Mail(
      1L,
      "Pacote Atualizado",
      "Entregador X",
      10L,
      100L,
      LocalDate.now(),
      "101",
      null,
      null,
      false,
      null,
      false
    );

    MailDTO savedDTO = new MailDTO(
      1L,
      "Pacote Atualizado",
      "Entregador X",
      10L,
      100L,
      LocalDate.now().format(formatter),
      "101",
      null,
      null,
      false,
      null,
      false
    );

    when(mapper.toEntity(inputDTO)).thenReturn(mailEntity);
    when(persistencePort.save(mailEntity)).thenReturn(savedMail);
    when(mapper.toDto(savedMail)).thenReturn(savedDTO);

    MailDTO result = useCase.updateMail(inputDTO);

    assertNotNull(result);
    assertEquals(savedDTO.getId(), result.getId());
    assertEquals(savedDTO.getDescription(), result.getDescription());
    verify(messageOutputPort, never()).sendNotification(any());
  }
}