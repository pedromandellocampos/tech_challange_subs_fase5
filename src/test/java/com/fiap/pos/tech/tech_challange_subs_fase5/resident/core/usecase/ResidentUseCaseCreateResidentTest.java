// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/resident/core/usecase/ResidentUseCaseCreateResidentTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResidentUseCaseCreateResidentTest {

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
  void createResident_success() {
    ResidentDTO dto = new ResidentDTO(null, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    Resident entity = new Resident(null, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    Resident savedEntity = new Resident(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    ResidentDTO savedDto = new ResidentDTO(1L, "Nome", "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.save(entity)).thenReturn(savedEntity);
    when(mapper.toDto(savedEntity)).thenReturn(savedDto);

    ResidentDTO result = useCase.createResident(dto);

    assertNotNull(result);
    assertEquals("Nome", result.getName());
    assertEquals("email@teste.com", result.getEmail());
    assertEquals("101", result.getApartment());
    assertTrue(result.isActive());
  }

  @Test
  void createResident_missingName_throwsException() {
    ResidentDTO dto = new ResidentDTO(null, null, "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);
    Resident entity = new Resident(null, null, "email@teste.com", "senha", "999999999", "101", LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createResident(dto));
    assertEquals("Name cannot be null or empty", ex.getMessage());
  }

  @Test
  void createResident_missingEmail_throwsException() {
    ResidentDTO dto = new ResidentDTO(null, "Nome", null, "senha", "999999999", "101", LocalDate.now(), true);
    Resident entity = new Resident(null, "Nome", null, "senha", "999999999", "101", LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createResident(dto));
    assertEquals("Email cannot be null or empty", ex.getMessage());
  }

  @Test
  void createResident_missingPhone_throwsException() {
    ResidentDTO dto = new ResidentDTO(null, "Nome", "email@teste.com", "senha", null, "101", LocalDate.now(), true);
    Resident entity = new Resident(null, "Nome", "email@teste.com", "senha", null, "101", LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createResident(dto));
    assertEquals("Phone cannot be null or empty", ex.getMessage());
  }

  @Test
  void createResident_missingApartment_throwsException() {
    ResidentDTO dto = new ResidentDTO(null, "Nome", "email@teste.com", "senha", "999999999", null, LocalDate.now(), true);
    Resident entity = new Resident(null, "Nome", "email@teste.com", "senha", "999999999", null, LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createResident(dto));
    assertEquals("Apartment cannot be null or empty", ex.getMessage());
  }

  @Test
  void createResident_missingBirthDate_throwsException() {
    ResidentDTO dto = new ResidentDTO(null, "Nome", "email@teste.com", "senha", "999999999", "101", null, true);
    Resident entity = new Resident(null, "Nome", "email@teste.com", "senha", "999999999", "101", null, true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createResident(dto));
    assertEquals("Date of birth cannot be null", ex.getMessage());
  }
}