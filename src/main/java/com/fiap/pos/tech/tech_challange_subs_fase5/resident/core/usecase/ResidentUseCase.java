package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output.ResidentPersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentUseCase implements ResidentUseCaseInputPort {


  private ResidentPersistenceOutputPort residentPersistenceOutputPort;
  private ResidentMapper residentMapper;


  @Override
  public ResidentDTO createResident(ResidentDTO ResidentDTO) {

    Resident resident = residentMapper.toEntity(ResidentDTO);

    if (resident.getEmail() == null || resident.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (resident.getPhone() == null || resident.getPhone().isEmpty()) {
      throw new IllegalArgumentException("Phone cannot be null or empty");
    }

    validateResident(resident);
    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }

  @Override
  public ResidentDTO updateResident(ResidentDTO ResidentDTO) {

    Resident resident = residentMapper.toEntity(ResidentDTO);
    var existingResident = getResidentById(resident.getId());

    if (resident.getPassword() == null) {
      resident.setPassword(existingResident.getPassword());
    }

    validateResident(resident);
    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }

  @Override
  public ResidentDTO getResidentById(Long id) {

    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    return residentMapper.toDto(resident);
  }

  @Override
  public ResidentDTO getResidentByEmail(String email) {

    Resident resident = residentPersistenceOutputPort.findByEmail(email).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    return residentMapper.toDto(resident);
  }

  @Override
  public List<ResidentDTO> listAllResidents(int page, int size) {

    List<ResidentDTO> residentDTOList =  residentPersistenceOutputPort.listAll(page, size).stream().map(resident -> residentMapper.toDto(resident)).collect(
      Collectors.toList());

    return residentDTOList;
  }

  @Override
  public boolean deleteResident(Long id) {

    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    // Delete resident
    return residentPersistenceOutputPort.deleteById(id);
  }

  @Override
  public ResidentDTO inactivateResident(Long id) {

    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    resident.setActive(false);
    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }


  @Override
  public ResidentDTO changeResidentPassword(Long id, String newPassword) {
    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("Employee not found");
    });

    resident.setPassword(newPassword);
    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }


  public void validateResident(Resident resident) {
    if (resident.getEmail() == null || resident.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    } else {
        Resident residentEmail = residentPersistenceOutputPort.findByEmail(resident.getEmail()).orElse(null);
        if(residentEmail != null && resident.getId() != null && residentEmail.getId() != resident.getId()) {
          throw new IllegalArgumentException("Email already exists");
        }
      if(residentEmail != null && resident.getId() == null) {
        throw new IllegalArgumentException("Email already exists");
      }
      }
    }

  public List<ResidentDTO> getResidentByUnity(String unity) {
    List<Resident> resident = residentPersistenceOutputPort.findByUnity(unity);
    return resident.stream().map(residentMapper::toDto).toList();
  }


}
