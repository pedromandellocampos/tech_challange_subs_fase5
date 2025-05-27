package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeUseCaseGetEmployeeByIdTest {


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
  void getEmployeeById_success() {
    Employee employee = new Employee(1L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);
    EmployeeDTO
      dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(persistencePort.findById(1L)).thenReturn(Optional.of(employee));
    when(mapper.toDto(employee)).thenReturn(dto);

    EmployeeDTO result = useCase.getEmployeeById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Nome", result.getName());
  }

  @Test
  void getEmployeeById_notFound_throwsException() {
    when(persistencePort.findById(2L)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.getEmployeeById(2L));
    assertEquals("Employee not found", ex.getMessage());
  }
}