// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/employee/core/usecases/EmployeeUseCaseChangeEmployeePasswordTest.java
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

class EmployeeUseCaseChangeEmployeePasswordTest {

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
  void changeEmployeePassword_success() {
    Long id = 1L;
    String newPassword = "novaSenha";
    Employee employee = new Employee(id, "Nome", "email@teste.com", "senhaAntiga", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee updatedEmployee = new Employee(id, "Nome", "email@teste.com", newPassword, "999999999", LocalDate.now(), LocalDate.now(), true);
    EmployeeDTO dto = new EmployeeDTO(id, "Nome", "email@teste.com", newPassword, "999999999", LocalDate.now(), LocalDate.now(), true);

    when(persistencePort.findById(id)).thenReturn(Optional.of(employee));
    when(persistencePort.save(employee)).thenReturn(updatedEmployee);
    when(mapper.toDto(updatedEmployee)).thenReturn(dto);

    EmployeeDTO result = useCase.changeEmployeePassword(id, newPassword);

    assertNotNull(result);
    assertEquals(newPassword, result.getPassword());
    verify(persistencePort).save(employee);
  }

  @Test
  void changeEmployeePassword_employeeNotFound_throwsException() {
    Long id = 2L;
    String newPassword = "novaSenha";
    when(persistencePort.findById(id)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.changeEmployeePassword(id, newPassword));
    assertEquals("Employee not found", ex.getMessage());
  }
}