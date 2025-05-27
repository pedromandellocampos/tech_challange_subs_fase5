package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Data
@AllArgsConstructor
public class ResidentPersistenceOutputPortImpl implements ResidentPersistenceOutputPort {

  ResidentEntityMapper residentEntityMapper;
  ResidentJPARepository jpaRepository;

  @Override
  public Resident save(Resident Resident) {
    ResidentEntity residentEntity = residentEntityMapper.toEntity(Resident);
    ResidentEntity savedResidentEntity = jpaRepository.save(residentEntity);
    return residentEntityMapper.toDomain(savedResidentEntity);
  }

  @Override
  public boolean delete(Resident Resident) {
    ResidentEntity residentEntity = residentEntityMapper.toEntity(Resident);
    jpaRepository.delete(residentEntity);
    return true;
  }

  @Override
  public boolean deleteById(Long id) {
    jpaRepository.deleteById(id);
    return true;
  }

  @Override
  public List<Resident> listAll(int page, int size) {
     Page<ResidentEntity> residentPage = jpaRepository.findAll(PageRequest.of(page, size));
    List<Resident> residentList = residentPage.toList().stream().map(residentEntityMapper::toDomain).collect(Collectors.toList());

    return residentList;
  }

  @Override
  public Optional<Resident> findById(Long id) {

      return jpaRepository.findById(id).map(residentEntityMapper::toDomain);
  }

  @Override
  public Optional<Resident> findByEmail(String email) {
      return jpaRepository.findByEmail(email).map(residentEntityMapper::toDomain);
  }

  @Override
  public Optional<Resident> findByPhone(String phone) {
    return Optional.empty();
  }
}
