package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.ResidentialUnity;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentialUnityDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentialUnityMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentialUnityUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentialUnityPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ResidentialUnityUseCase implements ResidentialUnityUseCaseInputPort {

  ResidentialUnityMapper residentialUnityMapper;
  ResidentialUnityPersistenceOutputPort residentialUnityPersistenceOutputPort;

  @Override
  public ResidentialUnityDTO createResidentialUnity(ResidentialUnityDTO residentialUnityDTO) {
    ResidentialUnity residentialUnity = residentialUnityMapper.toDomain(residentialUnityDTO);
    ResidentialUnity residentialUnityCreated = residentialUnityPersistenceOutputPort.createResidentialUnity(residentialUnity);
    return residentialUnityMapper.toDTO(residentialUnityCreated);
  }

  @Override
  public ResidentialUnityDTO getResidentialUnity(Long id) {
    ResidentialUnity residentialUnity = residentialUnityPersistenceOutputPort.getResidentialUnity(id);
    return residentialUnityMapper.toDTO(residentialUnity);
  }

  @Override
  public ResidentialUnityDTO updateResidentialUnity(ResidentialUnityDTO residentialUnityDTO) {
    ResidentialUnity residentialUnity = residentialUnityMapper.toDomain(residentialUnityDTO);
    ResidentialUnity residentialUnityUpdated = residentialUnityPersistenceOutputPort.updateResidentialUnity(residentialUnity);
    return residentialUnityMapper.toDTO(residentialUnityUpdated);
  }

  @Override
  public void deleteResidentialUnity(Long id) {
    residentialUnityPersistenceOutputPort.deleteResidentialUnity(id);
  }

  @Override
  public ResidentialUnityDTO getResidentialUnityByNumber(String number) {
    ResidentialUnity residentialUnity = residentialUnityPersistenceOutputPort.getResidentialUnityByNumber(number);
    return residentialUnityMapper.toDTO(residentialUnity);
  }

  @Override
  public List<ResidentialUnityDTO> ListAllResidentialUnity(int page, int size) {
    List<ResidentialUnity> residentialUnityList = residentialUnityPersistenceOutputPort.listAllResidentialUnity(page, size);
    List<ResidentialUnityDTO> residentialUnityDTOList = residentialUnityList.stream().map(residentialUnityMapper::toDTO).collect(
      Collectors.toList());
    return residentialUnityDTOList;
  }
}
