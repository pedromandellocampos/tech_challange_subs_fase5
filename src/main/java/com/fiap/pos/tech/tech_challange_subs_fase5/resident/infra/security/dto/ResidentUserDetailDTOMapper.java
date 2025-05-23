package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentUserDetailDTOMapper {

  ResidentUserDetailDTO toUserDetail(ResidentDTO residentDTO);
  ResidentDTO toDTO(ResidentUserDetailDTO residentUserDetailDTO);

}
