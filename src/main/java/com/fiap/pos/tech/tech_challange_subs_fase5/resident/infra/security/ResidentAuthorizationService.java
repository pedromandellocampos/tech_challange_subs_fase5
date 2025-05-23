package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security;


import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTOMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ResidentAuthorizationService implements UserDetailsService {

  ResidentUseCaseInputPort residentUseCaseInputPort;
  ResidentUserDetailDTOMapper residentUserDetailDTOMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var residentDTO = residentUseCaseInputPort.getResidentByEmail(username);
    return residentUserDetailDTOMapper.toUserDetail(residentDTO);
  }
}
