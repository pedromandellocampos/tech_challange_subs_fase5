package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EmployeeJPAPersistenceOutputPortImpl.class, EmployeeJPAEntityMapperImpl.class})
class EmployeeJPAPersistenceOutputPortImplListAllTest {

  @Autowired
  EmployeeJPAPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void listAll_success() {
    for (int i = 1; i <= 5; i++) {
      Employee employee = new Employee();
      employee.setName("Funcionario " + i);
      employee.setEmail("func" + i + "@email.com");
      employee.setPassword("senha" + i);
      employee.setPhone("1199999999" + i);
      employee.setDateOfBirth(LocalDate.of(1990, 1, i));
      employee.setHireDate(LocalDate.of(2024, 1, i));
      employee.setActive(true);
      persistenceOutputPort.save(employee);
    }

    List<Employee> page0 = persistenceOutputPort.listAll(0, 3);
    assertEquals(3, page0.size());
    assertEquals("Funcionario 1", page0.get(0).getName());

    List<Employee> page1 = persistenceOutputPort.listAll(1, 3);
    assertEquals(2, page1.size());
    assertEquals("Funcionario 4", page1.get(0).getName());
  }
}