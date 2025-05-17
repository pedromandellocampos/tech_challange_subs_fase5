package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentialUnityDTO;

import java.util.List;

public interface ResidentialUnityUseCaseInputPort {

  ResidentialUnityDTO createResidentialUnity(ResidentialUnityDTO residentialUnityDTO);
  ResidentialUnityDTO getResidentialUnity(Long id);
  ResidentialUnityDTO updateResidentialUnity(ResidentialUnityDTO residentialUnityDTO);
  void deleteResidentialUnity(Long id);
  ResidentialUnityDTO getResidentialUnityByNumber(String number);
  List<ResidentialUnityDTO> ListAllResidentialUnity(int page, int size);

}
