package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MailMapper {

  MailDTO toDto(Mail packages);
  Mail toEntity(MailDTO mailDTO);
}
