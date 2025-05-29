package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateMailDTOToDTOMapper {
  @Mapping(target = "residentAcknowledgedById", source = "residentAcknowledgedById")
  @Mapping(target = "acknowledgmentTimestamp", source = "acknowledgmentTimestamp")
  @Mapping(target = "receivedByResident", source = "receivedByResident")
  MailDTO toDto(UpdateMailDTO updateMailDTO);
}
