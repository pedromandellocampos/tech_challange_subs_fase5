package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.configuration;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.EmployeeUseCase;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfiguration {


  @Bean
  public EmployeeUseCase employeeUseCase(EmployeePersistenceOutputPort employeePersistenceOutputPort, EmployeeMapper employeeMapper) {
    return new EmployeeUseCase(employeePersistenceOutputPort, employeeMapper);
  }


}
