package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateMailDTOToDTOMapper {
  MailDTO toDto(UpdateMailDTO updateMailDTO);
}
