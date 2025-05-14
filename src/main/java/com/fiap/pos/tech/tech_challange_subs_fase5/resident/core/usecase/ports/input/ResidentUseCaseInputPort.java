package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input;


import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;

import java.util.List;

public interface ResidentUseCaseInputPort {

  ResidentDTO createEmployee(ResidentDTO ResidentDTO);
  ResidentDTO updateEmployee(ResidentDTO ResidentDTO);
  ResidentDTO getEmployeeById(Long id);
  ResidentDTO getEmployeeByEmail(String email);
  List<ResidentDTO> listAllEmployees(int page, int size);
  boolean deleteEmployee(Long id);
  ResidentDTO inactivateEmployee(Long id);


}
