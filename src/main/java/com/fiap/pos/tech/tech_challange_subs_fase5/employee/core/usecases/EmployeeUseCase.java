package com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.model.Employee;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.output.EmployeePersistenceOutputPort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EmployeeUseCase implements EmployeeUseCaseInputPort {

  private EmployeePersistenceOutputPort employeePersistenceOutputPort;
  private EmployeeMapper employeeMapper;

  @Override
  public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

    Employee employee = employeeMapper.toEntity(employeeDTO);

    // Validate employee data
    if (employee.getName() == null || employee.getName().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
      throw new IllegalArgumentException("Phone cannot be null or empty");
    }
    if (employee.getDateOfBirth() == null) {
      throw new IllegalArgumentException("Date of birth cannot be null");
    }
    if (employee.getHireDate() == null) {
      throw new IllegalArgumentException("Hire date cannot be null");
    }
    validateEmployee(employee);
    return employeeMapper.toDto(employeePersistenceOutputPort.save(employee));
  }

  @Override
  public EmployeeDTO updateEmployee(EmployeeDTO employeeDto) {

    Employee employee = employeeMapper.toEntity(employeeDto);
    Employee existingEmployee = employeePersistenceOutputPort.findById(employee.getId())
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

    validateEmployee(employee);
    // Validate employee data
    if(employee.getPassword() == null || employee.getPassword().isEmpty()) {
      employee.setPassword(existingEmployee.getPassword());
    }

    return employeeMapper.toDto(employeePersistenceOutputPort.save(employee));
  }

  @Override
  public EmployeeDTO getEmployeeById(Long id) {
    Employee employee = employeePersistenceOutputPort.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("Employee not found");
    });
    return employeeMapper.toDto(employee);
  }

  @Override
  public EmployeeDTO getEmployeeByEmail(String email) {
    Employee employee = employeePersistenceOutputPort.findByEmail(email).orElseThrow(() -> {
      throw new IllegalArgumentException("Employee not found");
    });
    return employeeMapper.toDto(employee);
  }

  @Override
  public List<EmployeeDTO> listAllEmployees(int page, int size) {

    List<Employee> employeeList =  employeePersistenceOutputPort.listAll(page, size);
    List<EmployeeDTO> employeeListDTO = employeeList.stream().map(employeeMapper::toDto).collect(Collectors.toList());

    return employeeListDTO;
  }

  @Override
  public boolean deleteEmployee(Long id) {

    Employee employee = employeePersistenceOutputPort.findById(id).orElseThrow(() ->
        new IllegalArgumentException("Employee not found"));

    return employeePersistenceOutputPort.delete(employee);

  }

  @Override
  public EmployeeDTO inactivateEmployee(Long id) {

    Employee employee = employeePersistenceOutputPort.findById(id).orElseThrow(() ->
      new IllegalArgumentException("Employee not found"));

    // Check if employee exists
    if (employeePersistenceOutputPort.findById(employee.getId()).isEmpty()) {
      throw new IllegalArgumentException("Employee not found");
    }
    employee.setActive(false);

    return employeeMapper.toDto(employeePersistenceOutputPort.save(employee));
  }

  @Override
  public EmployeeDTO changeEmployeePassword(Long id, String newPassword) {
    Employee employee = employeePersistenceOutputPort.findById(id).orElseThrow(() -> {
      throw new IllegalArgumentException("Employee not found");
    });

    employee.setPassword(newPassword);
    return employeeMapper.toDto(employeePersistenceOutputPort.save(employee));
  }


  public void validateEmployee(Employee employee) {
    if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    } else {
      Employee residentEmail = employeePersistenceOutputPort.findByEmail(employee.getEmail()).orElse(null);
      if(residentEmail != null && employee.getId() != null && residentEmail.getId() != employee.getId()) {
        throw new IllegalArgumentException("Email already exists");
      }
      if(residentEmail != null && employee.getId() == null) {
        throw new IllegalArgumentException("Email already exists");
      }
    }
  }


}
