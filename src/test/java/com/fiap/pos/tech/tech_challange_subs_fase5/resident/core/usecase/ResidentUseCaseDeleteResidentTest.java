package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResidentUseCaseDeleteResidentTest {

  private ResidentPersistenceOutputPort persistencePort;
  private ResidentMapper mapper;
  private ResidentUseCase useCase;

  @BeforeEach
  void setUp() {
    persistencePort = mock(ResidentPersistenceOutputPort.class);
    mapper = mock(ResidentMapper.class);
    useCase = new ResidentUseCase(persistencePort, mapper);
  }

  @Test
  void deleteResident_success() {
    Resident entity = new Resident(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);

    when(persistencePort.findById(1L)).thenReturn(Optional.of(entity));
    when(persistencePort.deleteById(1L)).thenReturn(true);

    boolean result = useCase.deleteResident(1L);

    assertTrue(result);
    verify(persistencePort).deleteById(1L);
  }

  @Test
  void deleteResident_notFound_throwsException() {
    when(persistencePort.findById(2L)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.deleteResident(2L));
    assertEquals("Resident not found", ex.getMessage());
  }
}