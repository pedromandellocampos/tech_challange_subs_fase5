package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

  EmployeeUseCaseInputPort employeeUseCaseInputPort;
  EmployeeUserDetailDTOMapper employeeUserDetailDTOMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail(username);
    return employeeUserDetailDTOMapper.toUserDetail(employeeDTO);
  }

}
