package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MailMapper {

  @Mapping(target = "deliveryTimestamp", dateFormat = "dd/MM/yyyy")
  MailDTO toDto(Mail packages);
  @Mapping(target = "deliveryTimestamp", dateFormat = "dd/MM/yyyy")
  Mail toEntity(MailDTO mailDTO);
}
