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
import static org.mockito.Mockito.*;

class EmployeeUseCaseGetEmployeeByMailTest {

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
  void getEmployeeByEmail_success() {
    String email = "email@teste.com";
    Employee employee = new Employee(1L, "Nome", email, "senha", "999999999", LocalDate.now(), LocalDate.now(), true);
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", email, "senha", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(persistencePort.findByEmail(email)).thenReturn(Optional.of(employee));
    when(mapper.toDto(employee)).thenReturn(dto);

    EmployeeDTO result = useCase.getEmployeeByEmail(email);

    assertNotNull(result);
    assertEquals(email, result.getEmail());
  }

  @Test
  void getEmployeeByEmail_notFound_throwsException() {
    String email = "naoexiste@teste.com";
    when(persistencePort.findByEmail(email)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.getEmployeeByEmail(email));
    assertEquals("Employee not found", ex.getMessage());
  }
}