package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentDTOToReturnMapper {
  ResidentDTOToReturn toDto(ResidentDTO residentDTOToReturn);
  ResidentDTO toEntity(ResidentDTOToReturn residentDTOToReturn);
}
