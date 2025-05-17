package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.configuration;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ResidentialUnityUseCase;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentialUnityMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentialUnityUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentialUnityPersistenceOutputPort;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ResidentialUnityUseCaseConfiguration {

  @Bean
  public ResidentialUnityUseCaseInputPort residentialUnityUseCaseInputPort(
    ResidentialUnityMapper residentialUnityMapper, ResidentialUnityPersistenceOutputPort residentialUnityPersistenceOutputPort) {
    return new ResidentialUnityUseCase( residentialUnityMapper, residentialUnityPersistenceOutputPort);
  }
}
