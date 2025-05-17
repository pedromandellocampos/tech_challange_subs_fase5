package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.ResidentialUnity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentialUnityMapper {

  ResidentialUnityDTO toDTO(ResidentialUnity residentialUnity);
  ResidentialUnity toDomain(ResidentialUnityDTO residentialUnityDTO);

}
