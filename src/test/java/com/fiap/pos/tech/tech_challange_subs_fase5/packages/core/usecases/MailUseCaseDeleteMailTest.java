// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/packages/core/usecases/MailUseCaseDeleteMailTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailMessageOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailUseCaseDeleteMailTest {

  private MailPersistenceOutputPort persistencePort;
  private MailMapper mapper;
  private MailMessageOutputPort messageOutputPort;
  private MailUseCase useCase;

  @BeforeEach
  void setUp() {
    persistencePort = mock(MailPersistenceOutputPort.class);
    mapper = mock(MailMapper.class);
    messageOutputPort = mock(MailMessageOutputPort.class);
    useCase = new MailUseCase(persistencePort, mapper, messageOutputPort);
  }

  @Test
  void deleteMail_success() {
    Long id = 1L;

    when(persistencePort.delete(id)).thenReturn(true);

    boolean result = useCase.deleteMail(id);

    assertTrue(result);
    verify(persistencePort, times(1)).delete(id);
  }

  @Test
  void deleteMail_error() {
    Long id = 2L;

    when(persistencePort.delete(id)).thenThrow(new RuntimeException("Erro ao deletar"));

    //boolean result = useCase.deleteMail(id);

    assertThrows(RuntimeException.class, () -> useCase.deleteMail(id));
    verify(persistencePort, times(1)).delete(id);
  }
}