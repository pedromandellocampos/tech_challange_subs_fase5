// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/employee/infra/repository/employee/EmployeeJPAPersistenceOutputPortImplDeleteTest.java
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
class EmployeeJPAPersistenceOutputPortImplDeleteTest {

  @Autowired
  EmployeeJPAPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void delete_success() {
    Employee employee = new Employee();
    employee.setName("João Souza");
    employee.setEmail("joao@email.com");
    employee.setPassword("senha123");
    employee.setPhone("11988888888");
    employee.setDateOfBirth(LocalDate.of(1985, 5, 20));
    employee.setHireDate(LocalDate.of(2023, 2, 15));
    employee.setActive(true);

    Employee saved = persistenceOutputPort.save(employee);

    boolean deleted = persistenceOutputPort.delete(saved);
    assertTrue(deleted);

    Optional<Employee> found = persistenceOutputPort.findByEmail("joao@email.com");
    assertTrue(found.isEmpty());
  }
}