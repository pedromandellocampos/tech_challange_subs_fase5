// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/packages/core/usecases/MailUseCaseGetMailByIdTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailUseCaseGetMailByIdTest {

  private MailPersistenceOutputPort persistencePort;
  private MailMapper mapper;
  private MailMessageOutputPort messageOutputPort;
  private ResidentUseCaseInputPort residentUseCaseInputPort;
  private MailUseCase useCase;

  @BeforeEach
  void setUp() {
    persistencePort = mock(MailPersistenceOutputPort.class);
    mapper = mock(MailMapper.class);
    messageOutputPort = mock(MailMessageOutputPort.class);
    residentUseCaseInputPort = mock(ResidentUseCaseInputPort.class);

    useCase = new MailUseCase(persistencePort, mapper, messageOutputPort, residentUseCaseInputPort);
  }

  @Test
  void getMailById_success() {
    Long id = 1L;
    Mail mail = new Mail();
    mail.setId(id);
    MailDTO mailDTO = new MailDTO();
    mailDTO.setId(id);

    when(persistencePort.getMailById(id)).thenReturn(Optional.of(mail));
    when(mapper.toDto(mail)).thenReturn(mailDTO);

    MailDTO result = useCase.getMailById(id);

    assertNotNull(result);
    assertEquals(id, result.getId());
    verify(persistencePort).getMailById(id);
    verify(mapper).toDto(mail);
  }

  @Test
  void getMailById_notFound() {
    Long id = 2L;
    when(persistencePort.getMailById(id)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> useCase.getMailById(id));
    verify(persistencePort).getMailById(id);
    verify(mapper, never()).toDto(any());
  }
}