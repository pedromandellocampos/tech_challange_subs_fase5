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

class ResidentUseCaseUpdateResidentTest {

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
  void updateResident_success() {
    ResidentDTO dto = new ResidentDTO(1L, "Novo Nome", "novo@email.com", "novaSenha", "888888888", "202", LocalDate.now(), true);
    Resident entity = new Resident(1L, "Novo Nome", "novo@email.com", "novaSenha", "888888888", "202", LocalDate.now(), true);
    Resident existing = new Resident(1L, "Nome Antigo", "antigo@email.com", "senhaAntiga", "999999999", "101", LocalDate.now(), true);
    Resident saved = new Resident(1L, "Novo Nome", "novo@email.com", "novaSenha", "888888888", "202", LocalDate.now(), true);
    ResidentDTO savedDto = new ResidentDTO(1L, "Novo Nome", "novo@email.com", "novaSenha", "888888888", "202", LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
    when(persistencePort.save(entity)).thenReturn(saved);
    when(mapper.toDto(saved)).thenReturn(savedDto);

    ResidentDTO result = useCase.updateResident(dto);

    assertNotNull(result);
    assertEquals("Novo Nome", result.getName());
    assertEquals("202", result.getApartment());
    verify(persistencePort).save(entity);
  }

  @Test
  void updateResident_notFound_throwsException() {
    ResidentDTO dto = new ResidentDTO(2L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    Resident entity = new Resident(2L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.findById(2L)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.updateResident(dto));
    assertEquals("Resident not found", ex.getMessage());
  }
}