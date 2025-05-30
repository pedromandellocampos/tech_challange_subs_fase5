package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeUpdateDTOMapper {

  @Mapping(source="dateOfBirth" , target ="dateOfBirth", dateFormat = "dd/MM/yyyy")
  @Mapping(source="hireDate" , target ="hireDate", dateFormat = "dd/MM/yyyy")
  EmployeeDTO toDto(EmployeeUpdateDTO employeeUpdateDTO);

}
