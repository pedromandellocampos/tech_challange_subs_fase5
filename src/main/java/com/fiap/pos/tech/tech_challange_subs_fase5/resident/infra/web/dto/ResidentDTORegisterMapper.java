package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResidentDTORegisterMapper {

  @Mapping(source = "birthDate", target = "birthDate", dateFormat = "dd/MM/yyyy")
  ResidentDTORegister toDto(ResidentDTO residentDTORegister);
  @Mapping(source = "birthDate", target = "birthDate", dateFormat = "dd/MM/yyyy")
  ResidentDTO toEntity(ResidentDTORegister residentDTORegister);

}
