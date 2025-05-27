// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/resident/core/usecase/ResidentUseCaseGetResidentByEmailTest.java
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

class ResidentUseCaseGetResidentByEmailTest {

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
  void getResidentByEmail_success() {
    Resident entity = new Resident(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    ResidentDTO dto = new ResidentDTO(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);

    when(persistencePort.findByEmail("email@teste.com")).thenReturn(Optional.of(entity));
    when(mapper.toDto(entity)).thenReturn(dto);

    ResidentDTO result = useCase.getResidentByEmail("email@teste.com");

    assertNotNull(result);
    assertEquals("email@teste.com", result.getEmail());
    assertEquals("Nome", result.getName());
  }

  @Test
  void getResidentByEmail_notFound_throwsException() {
    when(persistencePort.findByEmail("naoexiste@teste.com")).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.getResidentByEmail("naoexiste@teste.com"));
    assertEquals("Resident not found", ex.getMessage());
  }
}