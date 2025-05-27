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
import static org.mockito.Mockito.when;

class EmployeeUseCaseUpdateEmployeeTest {


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
  void updateEmployee_success() {
    EmployeeDTO
      dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "novaSenha", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee
      entity = new Employee(1L, "Nome", "email@teste.com", "novaSenha", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee existing = new Employee(1L, "Nome", "email@teste.com", "senhaAntiga", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
    when(persistencePort.save(entity)).thenReturn(entity);
    when(mapper.toDto(entity)).thenReturn(dto);

    EmployeeDTO result = useCase.updateEmployee(dto);

    assertNotNull(result);
    assertEquals("novaSenha", result.getPassword());
    verify(persistencePort).save(entity);
  }

  @Test
  void updateEmployee_nullPassword_keepsOldPassword() {
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "email@teste.com", null, "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee entity = new Employee(1L, "Nome", "email@teste.com", null, "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee existing = new Employee(1L, "Nome", "email@teste.com", "senhaAntiga", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
    when(persistencePort.save(entity)).thenReturn(entity);
    when(mapper.toDto(entity)).thenReturn(dto);

    EmployeeDTO result = useCase.updateEmployee(dto);

    assertNotNull(result);
    // O método updateEmployee deve copiar a senha antiga para o novo objeto
    assertEquals("senhaAntiga", entity.getPassword());
    verify(persistencePort).save(entity);
  }

  @Test
  void updateEmployee_employeeNotFound_throwsException() {
    EmployeeDTO dto = new EmployeeDTO(2L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee entity = new Employee(2L, "Nome", "email@teste.com", "senha", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.findById(2L)).thenReturn(Optional.empty());

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.updateEmployee(dto));
    assertEquals("Employee not found", ex.getMessage());
  }

}