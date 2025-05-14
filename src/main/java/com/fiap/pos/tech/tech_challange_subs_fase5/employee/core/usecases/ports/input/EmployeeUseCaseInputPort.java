package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input;


import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;

import java.util.List;

public interface EmployeeUseCaseInputPort {

  EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
  EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);
  EmployeeDTO getEmployeeById(Long id);
  EmployeeDTO getEmployeeByEmail(String email);
  List<EmployeeDTO> listAllEmployees(int page, int size);
  boolean deleteEmployee(Long id);
  EmployeeDTO inactivateEmployee(Long id);


}
