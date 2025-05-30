package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailUseCaseReceiveEmailTest {

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
  void receiveMail_success() {
    MailDTO inputDTO = new MailDTO(1L, "Pacote", "Entregador", 10L, 100L, "01/01/2024", "101", 200L, formatter.format( LocalDate.now()), false, null, false);
    Mail inputMail = new Mail(1L, "Pacote", "Entregador", 10L, 100L, LocalDate.now(), "101", 200L, LocalDate.now(), false, null, false);
    Mail savedMail = new Mail(1L, "Pacote", "Entregador", 10L, 100L, LocalDate.now(), "101", 200L, LocalDate.now(), true, null, false);
    MailDTO outputDTO = new MailDTO(1L, "Pacote", "Entregador", 10L, 100L, "01/01/2024", "101", 200L, formatter.format( LocalDate.now()), true, null, false);

    when(mapper.toEntity(inputDTO)).thenReturn(inputMail);
    when(persistencePort.getMailById(1L)).thenReturn(Optional.of(inputMail));
    when(persistencePort.save(any(Mail.class))).thenReturn(savedMail);
    when(mapper.toDto(savedMail)).thenReturn(outputDTO);
    when(residentUseCaseInputPort.getResidentById(anyLong())).thenReturn(new ResidentDTO(
      1L,
      "Nome do Morador",
      "email@exemplo.com",
      "senha123",
      "11999999999",
      "101",
      LocalDate.of(1997, 5, 20),
      true
    ));

    MailDTO result = useCase.receiveMail(inputDTO);

    assertNotNull(result);
    assertTrue(result.isReceivedByResident());
    verify(persistencePort).getMailById(1L);
    verify(persistencePort).save(any(Mail.class));
  }

  @Test
  void receiveMail_mailNotFound() {
    MailDTO inputDTO = new MailDTO();
    inputDTO.setId(99L);

    Mail inputMail = new Mail();
    inputMail.setId(99L);

    when(mapper.toEntity(inputDTO)).thenReturn(inputMail);
    when(persistencePort.getMailById(99L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> useCase.receiveMail(inputDTO));
    verify(persistencePort).getMailById(99L);
    verify(persistencePort, never()).save(any(Mail.class));
  }
}