package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResidentUseCaseInactivateResidentTest {

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
  void inactivateResident_success() {
    Resident entity = new Resident(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    Resident inactivatedEntity = new Resident(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), false);
    ResidentDTO inactivatedDto = new ResidentDTO(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), false);

    when(persistencePort.findById(1L)).thenReturn(Optional.of(entity));
    when(persistencePort.save(any(Resident.class))).thenReturn(inactivatedEntity);
    when(mapper.toDto(inactivatedEntity)).thenReturn(inactivatedDto);

    ResidentDTO result = useCase.inactivateResident(1L);

    assertNotNull(result);
    assertFalse(result.isActive());
    assertEquals(1L, result.getId());
  }

  @Test
  void inactivateResident_notFound_throwsException() {
    when(persistencePort.findById(2L)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.inactivateResident(2L));
    assertEquals("Resident not found", ex.getMessage());
  }
}