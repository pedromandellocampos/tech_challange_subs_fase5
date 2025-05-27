package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResidentUseCaseListAllResidentsTest {

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
  void listAllResidents_success() {
    Resident entity1 = new Resident(1L, "Nome1", "email1@teste.com", "senha1", "999999999", "101", LocalDate.now(), true);
    Resident entity2 = new Resident(2L, "Nome2", "email2@teste.com", "senha2", "888888888", "102", LocalDate.now(), true);
    ResidentDTO dto1 = new ResidentDTO(1L, "Nome1", "email1@teste.com", "senha1", "999999999", "101", LocalDate.now(), true);
    ResidentDTO dto2 = new ResidentDTO(2L, "Nome2", "email2@teste.com", "senha2", "888888888", "102", LocalDate.now(), true);

    when(persistencePort.listAll(0, 2)).thenReturn(Arrays.asList(entity1, entity2));
    when(mapper.toDto(entity1)).thenReturn(dto1);
    when(mapper.toDto(entity2)).thenReturn(dto2);

    List<ResidentDTO> result = useCase.listAllResidents(0, 2);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Nome1", result.get(0).getName());
    assertEquals("Nome2", result.get(1).getName());
  }
}