// src/test/java/com/fiap/pos/tech/tech_challange_subs_fase5/employee/infra/repository/employee/EmployeeJPAPersistenceOutputPortImplFindByPhoneTest.java
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
class EmployeeJPAPersistenceOutputPortImplFindByPhoneTest {

  @Autowired
  EmployeeJPAPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void findByPhone_success() {




    Employee employee = new Employee();
    employee.setName("Bruno Costa");
    employee.setEmail("bruno@email.com");
    employee.setPassword("senha123");
    employee.setPhone("11955555555");
    employee.setDateOfBirth(LocalDate.of(1991, 4, 12));
    employee.setHireDate(LocalDate.of(2020, 9, 5));
    employee.setActive(true);

    persistenceOutputPort.save(employee);

    Optional<Employee> found = persistenceOutputPort.findByPhone("11955555555");
    assertTrue(found.isPresent());
    assertEquals("Bruno Costa", found.get().getName());
  }

  @Test
  void findByPhone_notFound() {
    Optional<Employee> found = persistenceOutputPort.findByPhone("00000000000");
    assertTrue(found.isEmpty());
  }
}