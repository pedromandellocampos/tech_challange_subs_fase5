package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeePersistenceOutputPort {


  Employee save(Employee employee);

  boolean delete(Employee employee);
  List<Employee>  listAll(int page, int size);
  Optional<Employee> findById(Long id);
  Optional<Employee> findByEmail(String email);
  Optional<Employee> findByPhone(String phone);

}
