package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentMapper {

  ResidentDTO toDto(Resident resident);
  Resident toEntity(ResidentDTO residentDTO);

}
