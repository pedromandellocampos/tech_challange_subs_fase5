package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.configuration;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.EmployeeUseCase;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.MailUseCase;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output.MailPersistenceOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {

  @Bean
  public MailUseCaseInputPort mailUseCase(MailPersistenceOutputPort mailPersistenceOutputPort, MailMapper mailMapper) {
    return new MailUseCase(mailPersistenceOutputPort, mailMapper);
  }


}
