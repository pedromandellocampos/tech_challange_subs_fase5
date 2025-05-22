package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreteMailDTOToDTOMapper {

  MailDTO toDto(CreateMailDTO createMailDTO);

}
