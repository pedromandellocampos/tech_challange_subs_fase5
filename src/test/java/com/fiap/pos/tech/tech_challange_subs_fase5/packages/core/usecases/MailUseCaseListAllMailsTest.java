// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/packages/core/usecases/MailUseCaseListAllMailsTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.format.DateTimeFormatter;


class MailUseCaseListAllMailsTest {

  private MailPersistenceOutputPort persistencePort;
  private MailMapper mapper;
  private MailMessageOutputPort messageOutputPort;
  private ResidentUseCaseInputPort residentUseCaseInputPort;
  private MailUseCase useCase;


  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


  @BeforeEach
  void setUp() {
    persistencePort = mock(MailPersistenceOutputPort.class);
    mapper = mock(MailMapper.class);
    messageOutputPort = mock(MailMessageOutputPort.class);
    residentUseCaseInputPort = mock(ResidentUseCaseInputPort.class);
    useCase = new MailUseCase(persistencePort, mapper, messageOutputPort, residentUseCaseInputPort);
  }

  @Test
  void listAllMails_success() {
    Mail mail1 = new Mail(
      1L,
      "Pacote 1",
      "Entregador 1",
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

    Mail mail2 = new Mail(
      2L,
      "Pacote 2",
      "Entregador 2",
      20L,
      200L,
      LocalDate.now(),
      "102",
      null,
      null,
      false,
      null,
      false
    );

    MailDTO dto1 = new MailDTO(
      1L,
      "Pacote 1",
      "Entregador 1",
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
    MailDTO dto2 = new MailDTO(
      2L,
      "Pacote 2",
      "Entregador 2",
      20L,
      200L,
      LocalDate.now().format(formatter),
      "102",
      null,
      null,
      false,
      null,
      false
    );

    when(persistencePort.listMails(0, 2)).thenReturn(Arrays.asList(mail1, mail2));
    when(mapper.toDto(mail1)).thenReturn(dto1);
    when(mapper.toDto(mail2)).thenReturn(dto2);

    List<MailDTO> result = useCase.listAllMails(0, 2);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("101", result.get(0).getUnity());
    assertEquals("102", result.get(1).getUnity());
  }
}