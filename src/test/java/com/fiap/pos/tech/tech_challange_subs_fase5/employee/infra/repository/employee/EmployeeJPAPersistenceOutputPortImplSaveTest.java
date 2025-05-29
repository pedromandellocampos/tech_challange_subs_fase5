// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/employee/infra/repository/employee/EmployeeJPAPersistenceOutputPortImplSaveTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EmployeeJPAPersistenceOutputPortImpl.class, EmployeeJPAEntityMapperImpl.class})
class EmployeeJPAPersistenceOutputPortImplSaveTest {

  @Autowired
  EmployeeJPAPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void save_success() {
    Employee employee = new Employee();
    employee.setName("Maria Silva");
    employee.setEmail("maria@email.com");
    employee.setPassword("senha123");
    employee.setPhone("11999999999");
    employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    employee.setHireDate(LocalDate.of(2024, 1, 1));
    employee.setActive(true);

    Employee saved = persistenceOutputPort.save(employee);

    assertNotNull(saved.getId());
    assertEquals("Maria Silva", saved.getName());
    assertEquals("maria@email.com", saved.getEmail());
    assertEquals("11999999999", saved.getPhone());
    assertTrue(saved.isActive());
  }
}