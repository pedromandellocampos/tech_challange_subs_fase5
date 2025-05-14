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


  ResidentPersistenceOutputPort residentPersistenceOutputPort;
  ResidentMapper residentMapper;


  @Override
  public ResidentDTO createEmployee(ResidentDTO ResidentDTO) {

    Resident resident = residentMapper.toEntity(ResidentDTO);

    // Validate resident data
    if (resident.getName() == null || resident.getName().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (resident.getEmail() == null || resident.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (resident.getPhone() == null || resident.getPhone().isEmpty()) {
      throw new IllegalArgumentException("Phone cannot be null or empty");
    }
    if (resident.getApartment() == null || resident.getApartment().isEmpty()) {
      throw new IllegalArgumentException("Apartment cannot be null or empty");
    }
    if (resident.getBirthDate() == null) {
      throw new IllegalArgumentException("Date of birth cannot be null");
    }

    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }

  @Override
  public ResidentDTO updateEmployee(ResidentDTO ResidentDTO) {

    Resident resident = residentMapper.toEntity(ResidentDTO);

    // Validate resident data
    if (resident.getName() == null || resident.getName().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (resident.getEmail() == null || resident.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (resident.getPhone() == null || resident.getPhone().isEmpty()) {
      throw new IllegalArgumentException("Phone cannot be null or empty");
    }
    if (resident.getApartment() == null || resident.getApartment().isEmpty()) {
      throw new IllegalArgumentException("Apartment cannot be null or empty");
    }
    if (resident.getBirthDate() == null) {
      throw new IllegalArgumentException("Date of birth cannot be null");
    }

    if (resident.getId() == null) {
      throw new IllegalArgumentException("Id cannot be null");
    }

    // Check if resident exists
    if (residentPersistenceOutputPort.findByEmail(resident.getEmail()).isEmpty()) {
      throw new IllegalArgumentException("Resident not found");
    }

    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }

  @Override
  public ResidentDTO getEmployeeById(Long id) {

    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    return residentMapper.toDto(resident);
  }

  @Override
  public ResidentDTO getEmployeeByEmail(String email) {

    Resident resident = residentPersistenceOutputPort.findByEmail(email).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    return residentMapper.toDto(resident);
  }

  @Override
  public List<ResidentDTO> listAllEmployees(int page, int size) {

    List<ResidentDTO> residentDTOList =  residentPersistenceOutputPort.listAll(page, size).stream().map(resident -> residentMapper.toDto(resident)).collect(
      Collectors.toList());

    return residentDTOList;
  }

  @Override
  public boolean deleteEmployee(Long id) {

    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    // Delete resident
    return residentPersistenceOutputPort.deleteById(id);
  }

  @Override
  public ResidentDTO inactivateEmployee(Long id) {

    Resident resident = residentPersistenceOutputPort.findById(id).orElseThrow(() -> {
        throw new IllegalArgumentException("Resident not found");
      }
    );

    resident.setActive(false);
    return residentMapper.toDto(residentPersistenceOutputPort.save(resident));
  }
}
