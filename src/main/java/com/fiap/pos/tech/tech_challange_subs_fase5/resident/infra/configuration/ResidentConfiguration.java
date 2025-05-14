package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.configuration;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ResidentUseCase;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResidentConfiguration {

  @Bean
  public ResidentUseCaseInputPort residentUseCase(ResidentPersistenceOutputPort residentPersistenceOutputPort, ResidentMapper residentMapper) {
    return new ResidentUseCase(residentPersistenceOutputPort, residentMapper);
  }

}
