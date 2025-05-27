package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeUseCaseCreateEmployeeTest {

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
  void createEmployee_success() {
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "123456", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee entity = new Employee(1L, "Nome", "email@teste.com", "123456", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(persistencePort.save(entity)).thenReturn(entity);
    when(mapper.toDto(entity)).thenReturn(dto);

    EmployeeDTO result = useCase.createEmployee(dto);

    assertNotNull(result);
    assertEquals(dto.getEmail(), result.getEmail());
    verify(persistencePort).save(entity);
  }

  @Test
  void createEmployee_missingName_throwsException() {
    EmployeeDTO dto = new EmployeeDTO(1L, "", "email@teste.com", "123456", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee entity = new Employee(1L, "", "email@teste.com", "123456", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createEmployee(dto));
    assertEquals("Name cannot be null or empty", ex.getMessage());
  }

  @Test
  void createEmployee_missingEmail_throwsException() {
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "", "123456", "999999999", LocalDate.now(), LocalDate.now(), true);
    Employee entity = new Employee(1L, "Nome", "", "123456", "999999999", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createEmployee(dto));
    assertEquals("Email cannot be null or empty", ex.getMessage());
  }

  @Test
  void createEmployee_missingPhone_throwsException() {
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "123456", "", LocalDate.now(), LocalDate.now(), true);
    Employee entity = new Employee(1L, "Nome", "email@teste.com", "123456", "", LocalDate.now(), LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createEmployee(dto));
    assertEquals("Phone cannot be null or empty", ex.getMessage());
  }

  @Test
  void createEmployee_missingDateOfBirth_throwsException() {
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "123456", "999999999", null, LocalDate.now(), true);
    Employee entity = new Employee(1L, "Nome", "email@teste.com", "123456", "999999999", null, LocalDate.now(), true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createEmployee(dto));
    assertEquals("Date of birth cannot be null", ex.getMessage());
  }

  @Test
  void createEmployee_missingHireDate_throwsException() {
    EmployeeDTO dto = new EmployeeDTO(1L, "Nome", "email@teste.com", "123456", "999999999", LocalDate.now(), null, true);
    Employee entity = new Employee(1L, "Nome", "email@teste.com", "123456", "999999999", LocalDate.now(), null, true);

    when(mapper.toEntity(dto)).thenReturn(entity);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.createEmployee(dto));
    assertEquals("Hire date cannot be null", ex.getMessage());
  }
}