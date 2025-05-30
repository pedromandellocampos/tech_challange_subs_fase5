package com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTOMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

  private EmployeeUseCaseInputPort employeeUseCaseInputPort;
  private EmployeeUserDetailDTOMapper employeeUserDetailDTOMapper;
  private ResidentUseCaseInputPort residentUseCaseInputPort;
  private ResidentUserDetailDTOMapper residentUserDetailDTOMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
      ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail(username);
      UserDetails userDetails = residentUserDetailDTOMapper.toUserDetail(residentDTO);
      return userDetails;
    } catch (Exception e){
      EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail(username);
      return employeeUserDetailDTOMapper.toUserDetail(employeeDTO);
    }
  }

}
