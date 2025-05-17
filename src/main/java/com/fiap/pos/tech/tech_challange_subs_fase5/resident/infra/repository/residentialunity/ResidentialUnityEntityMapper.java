package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.residentialunity;


import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.ResidentialUnity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentialUnityEntityMapper {
   ResidentialUnity toDomain(ResidentialUnityEntity residentialUnityEntity);
   ResidentialUnityEntity toEntity(ResidentialUnity residentialUnity);
}
