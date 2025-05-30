package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input;


import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;

import java.util.List;

public interface ResidentUseCaseInputPort {

  ResidentDTO createResident(ResidentDTO ResidentDTO);
  ResidentDTO updateResident(ResidentDTO ResidentDTO);
  ResidentDTO getResidentById(Long id);
  ResidentDTO getResidentByEmail(String email);
  List<ResidentDTO> listAllResidents(int page, int size);
  boolean deleteResident(Long id);
  ResidentDTO inactivateResident(Long id);
  ResidentDTO changeResidentPassword(Long id, String newPassword);
  List<ResidentDTO> getResidentByUnity(String unity);


}
