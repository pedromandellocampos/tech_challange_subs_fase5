// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/packages/core/usecases/MailUseCaseConfirmNotificationTest.java
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

class MailUseCaseConfirmNotificationTest {

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
  void confirmNotification_success() {
    MailDTO inputDTO = new MailDTO();
    inputDTO.setId(1L);
    inputDTO.setResidentAcknowledgedById(123L);

    Mail inputMail = new Mail();
    inputMail.setId(1L);
    inputMail.setResidentAcknowledgedById(123L);

    Mail savedMail = new Mail();
    savedMail.setId(1L);
    savedMail.setResidentConfirmedMailId(123L);
    savedMail.setReceivedByResident(true);

    MailDTO outputDTO = new MailDTO();
    outputDTO.setId(1L);
    outputDTO.setResidentAcknowledgedById(123L);
    outputDTO.setReceivedByResident(true);

    when(mapper.toEntity(inputDTO)).thenReturn(inputMail);
    when(persistencePort.getMailById(1L)).thenReturn(Optional.of(inputMail));
    when(persistencePort.save(any(Mail.class))).thenReturn(savedMail);
    when(mapper.toDto(savedMail)).thenReturn(outputDTO);

    MailDTO result = useCase.confirmNotification(inputDTO);

    assertNotNull(result);
    assertTrue(result.isReceivedByResident());
    assertEquals(123L, result.getResidentAcknowledgedById());
    verify(persistencePort).getMailById(1L);
    verify(persistencePort).save(any(Mail.class));
  }

  @Test
  void confirmNotification_mailNotFound() {
    MailDTO inputDTO = new MailDTO();
    inputDTO.setId(99L);

    Mail inputMail = new Mail();
    inputMail.setId(99L);

    when(mapper.toEntity(inputDTO)).thenReturn(inputMail);
    when(persistencePort.getMailById(99L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> useCase.confirmNotification(inputDTO));
    verify(persistencePort).getMailById(99L);
    verify(persistencePort, never()).save(any(Mail.class));
  }
}