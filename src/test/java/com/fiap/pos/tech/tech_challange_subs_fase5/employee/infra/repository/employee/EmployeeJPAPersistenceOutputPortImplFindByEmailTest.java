// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/employee/infra/repository/employee/EmployeeJPAPersistenceOutputPortImplFindByEmailTest.java
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
class EmployeeJPAPersistenceOutputPortImplFindByEmailTest {

  @Autowired
  EmployeeJPAPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void findByEmail_success() {
    Employee employee = new Employee();
    employee.setName("Ana Paula");
    employee.setEmail("ana@email.com");
    employee.setPassword("senha123");
    employee.setPhone("11966666666");
    employee.setDateOfBirth(LocalDate.of(1992, 7, 15));
    employee.setHireDate(LocalDate.of(2021, 5, 10));
    employee.setActive(true);

    persistenceOutputPort.save(employee);

    Optional<Employee> found = persistenceOutputPort.findByEmail("ana@email.com");
    assertTrue(found.isPresent());
    assertEquals("Ana Paula", found.get().getName());
  }

  @Test
  void findByEmail_notFound() {
    Optional<Employee> found = persistenceOutputPort.findByEmail("naoexiste@email.com");
    assertTrue(found.isEmpty());
  }
}