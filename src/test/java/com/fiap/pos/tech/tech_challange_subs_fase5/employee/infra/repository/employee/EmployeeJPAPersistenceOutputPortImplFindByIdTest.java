// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/employee/infra/repository/employee/EmployeeJPAPersistenceOutputPortImplFindByIdTest.java
package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EmployeeJPAPersistenceOutputPortImpl.class, EmployeeJPAEntityMapperImpl.class})
class EmployeeJPAPersistenceOutputPortImplFindByIdTest {

  @Autowired
  EmployeeJPAPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void findById_success() {
    Employee employee = new Employee();
    employee.setName("Carlos Lima");
    employee.setEmail("carlos@email.com");
    employee.setPassword("senha123");
    employee.setPhone("11977777777");
    employee.setDateOfBirth(LocalDate.of(1988, 3, 10));
    employee.setHireDate(LocalDate.of(2022, 8, 1));
    employee.setActive(true);

    Employee saved = persistenceOutputPort.save(employee);

    Optional<Employee> found = persistenceOutputPort.findById(saved.getId());
    assertTrue(found.isPresent());
    assertEquals("Carlos Lima", found.get().getName());
  }

  @Test
  void findById_notFound() {
    Optional<Employee> found = persistenceOutputPort.findById(9999L);
    assertTrue(found.isEmpty());
  }
}