package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeUserDetailDTOMapper {

  EmployeeUserDetailDTO toUserDetail(EmployeeDTO employeeUserDetail);
  EmployeeDTO toDTO(EmployeeUserDetailDTO employeeUserDetailDTO);

}
