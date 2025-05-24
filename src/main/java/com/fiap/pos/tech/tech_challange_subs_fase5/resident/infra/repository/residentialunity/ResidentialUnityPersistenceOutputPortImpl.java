package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.residentialunity;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.ResidentialUnity;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentialUnityPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class ResidentialUnityPersistenceOutputPortImpl implements ResidentialUnityPersistenceOutputPort {

  private ResidentialUnityJPARepository residentialUnityRepository;
  private ResidentialUnityEntityMapper residentialUnityMapper;

  @Override
  public ResidentialUnity createResidentialUnity(ResidentialUnity residentialUnityDTO) {
    ResidentialUnityEntity residentialUnityEntity = residentialUnityMapper.toEntity(residentialUnityDTO);
    ResidentialUnityEntity residentialUnityEntityCreated = residentialUnityRepository.save(residentialUnityEntity);
    return residentialUnityMapper.toDomain(residentialUnityEntityCreated);
  }

  @Override
  public ResidentialUnity getResidentialUnity(Long id) {
    ResidentialUnityEntity residentialUnityEntity = residentialUnityRepository.findById(id).orElseThrow(() -> new RuntimeException("Residential Unity not found"));
    return residentialUnityMapper.toDomain(residentialUnityEntity);
  }

  @Override
  public ResidentialUnity updateResidentialUnity(ResidentialUnity residentialUnityDTO) {
    ResidentialUnityEntity residentialUnityEntity = residentialUnityMapper.toEntity(residentialUnityDTO);
    ResidentialUnityEntity residentialUnityEntityUpdated = residentialUnityRepository.save(residentialUnityEntity);
    return residentialUnityMapper.toDomain(residentialUnityEntityUpdated);
  }

  @Override
  public void deleteResidentialUnity(Long id) {
    ResidentialUnityEntity residentialUnityEntity = residentialUnityRepository.findById(id).orElseThrow(() -> new RuntimeException("Residential Unity not found"));
    residentialUnityRepository.delete(residentialUnityEntity);
  }

  @Override
  public ResidentialUnity getResidentialUnityByNumber(String number) {


    return null;
  }

  @Override
  public List<ResidentialUnity> listAllResidentialUnity(int page, int size) {
    return List.of();
  }
}
