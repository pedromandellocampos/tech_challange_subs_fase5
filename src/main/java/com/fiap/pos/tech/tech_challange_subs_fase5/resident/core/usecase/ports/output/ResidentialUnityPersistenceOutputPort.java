package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.ResidentialUnity;

import java.util.List;

public interface ResidentialUnityPersistenceOutputPort {
  ResidentialUnity createResidentialUnity(ResidentialUnity residentialUnityDTO);
  ResidentialUnity getResidentialUnity(Long id);
  ResidentialUnity updateResidentialUnity(ResidentialUnity residentialUnityDTO);
  void deleteResidentialUnity(Long id);
  ResidentialUnity getResidentialUnityByNumber(String number);
  List<ResidentialUnity> listAllResidentialUnity(int page, int size);
}
