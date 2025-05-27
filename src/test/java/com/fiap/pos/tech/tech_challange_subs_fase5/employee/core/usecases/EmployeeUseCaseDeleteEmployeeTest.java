package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeUseCaseDeleteEmployeeTest {

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
  void deleteEmployee_success() {
    Employee employee = new Employee(1L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(persistencePort.findById(1L)).thenReturn(Optional.of(employee));
    when(persistencePort.delete(employee)).thenReturn(true);

    boolean result = useCase.deleteEmployee(1L);

    assertTrue(result);
    verify(persistencePort).delete(employee);
  }

  @Test
  void deleteEmployee_notFound_throwsException() {
    when(persistencePort.findById(2L)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.deleteEmployee(2L));
    assertEquals("Employee not found", ex.getMessage());
  }
}