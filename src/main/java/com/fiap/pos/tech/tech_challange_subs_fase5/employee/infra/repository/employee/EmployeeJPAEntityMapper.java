package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeJPAEntityMapper {

  EmployeeJPAEntity toEntity(Employee employee);
  Employee toDomain(EmployeeJPAEntity employeeJPAEntity);

}
