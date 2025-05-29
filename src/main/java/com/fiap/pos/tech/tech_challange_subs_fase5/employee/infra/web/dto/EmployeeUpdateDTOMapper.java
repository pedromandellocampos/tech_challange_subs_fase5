package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeUpdateDTOMapper {

  EmployeeDTO toDto(EmployeeUpdateDTO employeeUpdateDTO);

}
