package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeDTO toDto(Employee employee);
  Employee toEntity(EmployeeDTO employeeDTO);

}
