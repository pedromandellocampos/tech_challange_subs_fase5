package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;


import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeJPAPersistenceOutputPortImpl implements EmployeePersistenceOutputPort {

  EmployeeJPARepository employeeJPARepository;
  EmployeeJPAEntityMapper employeeJPAEntityMapper;

  @Override
  public Employee save(Employee employee) {

    EmployeeJPAEntity employeeJPAEntity = employeeJPAEntityMapper.toEntity(employee);
    EmployeeJPAEntity savedEmployeeJPAEntity = employeeJPARepository.save(employeeJPAEntity);

    return employeeJPAEntityMapper.toDomain(savedEmployeeJPAEntity);
  }

  @Override
  public boolean delete(Employee employee) {

    EmployeeJPAEntity employeeJPAEntity = employeeJPARepository.findByEmail(employee.getEmail()).orElseThrow(() -> new RuntimeException("Employee not found"));
    employeeJPARepository.delete(employeeJPAEntity);
    return true;
  }

  @Override
  public List<Employee> listAll(int page, int size) {

    Page<EmployeeJPAEntity> employeeJPAEntityPage = employeeJPARepository.findAll(Pageable.ofSize(size).withPage(page));
    return employeeJPAEntityPage.getContent().stream().map(employeeJPAEntityMapper::toDomain).collect(Collectors.toList());
  }

  @Override
  public Optional<Employee> findById(Long id) {

    EmployeeJPAEntity employeeJPAEntity = employeeJPARepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

    if (employeeJPAEntity != null) {
      return Optional.of(employeeJPAEntityMapper.toDomain(employeeJPAEntity));
    } else
    {
      return Optional.empty();
    }

  }

  @Override
  public Optional<Employee> findByEmail(String email) {

    EmployeeJPAEntity employeeJPAEntity = employeeJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee not found"));

    if (employeeJPAEntity != null) {
      return Optional.of(employeeJPAEntityMapper.toDomain(employeeJPAEntity));
    } else
    {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Employee> findByPhone(String phone) {

    EmployeeJPAEntity employeeJPAEntity = employeeJPARepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("Employee not found"));

    if (employeeJPAEntity != null) {
      return Optional.of(employeeJPAEntityMapper.toDomain(employeeJPAEntity));
    } else
    {
      return Optional.empty();
    }
  }

}
