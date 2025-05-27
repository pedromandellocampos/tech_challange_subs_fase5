package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeUseCaseListAllEmployeesTest {

  private EmployeePersistenceOutputPort persistencePort;
  private EmployeeMapper mapper;
  private EmployeeUseCase useCase;

  @BeforeEach
  void setUp() {
    persistencePort = mock(EmployeePersistenceOutputPort.class);
    mapper = mock(EmployeeMapper.class);
    useCase = new EmployeeUseCase(persistencePort, mapper);
  }

  @Test
  void listAllEmployees_returnsEmployeeList() {
    Employee employee = new Employee(1L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(persistencePort.listAll(0, 10)).thenReturn(List.of(employee));
    when(mapper.toDto(employee)).thenReturn(dto);

    List<EmployeeDTO> result = useCase.listAllEmployees(0, 10);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Nome", result.get(0).getName());
  }

  @Test
  void listAllEmployees_returnsEmptyList() {
    when(persistencePort.listAll(0, 10)).thenReturn(Collections.emptyList());

    List<EmployeeDTO> result = useCase.listAllEmployees(0, 10);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}