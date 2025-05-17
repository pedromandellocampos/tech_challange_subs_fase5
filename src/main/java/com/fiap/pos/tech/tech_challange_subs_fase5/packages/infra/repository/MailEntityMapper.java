package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.model.Mail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MailEntityMapper {

  MailEntity toEntity(Mail mailEntity);
  Mail toDomain(MailEntity mailEntity);

}
