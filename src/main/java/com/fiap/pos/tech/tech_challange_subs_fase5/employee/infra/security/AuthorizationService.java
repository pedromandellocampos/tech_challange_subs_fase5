package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security;

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

  EmployeeUseCaseInputPort employeeUseCaseInputPort;
  EmployeeUserDetailDTOMapper employeeUserDetailDTOMapper;
  ResidentUseCaseInputPort residentUseCaseInputPort;
  ResidentUserDetailDTOMapper residentUserDetailDTOMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
      ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail(username);
      System.out.println("AQUI 123 -> " + residentDTO);
      UserDetails userDetails = residentUserDetailDTOMapper.toUserDetail(residentDTO);
      System.out.println("AQUI USER DETAISL " +  userDetails.getPassword());
      return userDetails;
    } catch (Exception e){
      EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail(username);
      return employeeUserDetailDTOMapper.toUserDetail(employeeDTO);
    }
  }

}
