package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentEntityMapper {

  ResidentEntity toEntity(Resident resident);
  Resident toDomain(ResidentEntity residentEntity);
}
