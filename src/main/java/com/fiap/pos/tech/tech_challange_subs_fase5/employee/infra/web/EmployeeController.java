package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security.JwtHandler;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.TokenReturnDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.*;
import com.fiap.pos.tech.tech_challange_subs_fase5.infra.web.exceptions.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

  private EmployeeUpdateDTOMapper employeeUpdateDTOMapper;
  private EmployeeUseCaseInputPort employeeUseCaseInputPort;
  private EmployeeDTORegisterMapper employeeDTORegisterMapper;
  private EmployeeDTOToReturnMapper employeeDTOToReturnMapper;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private JwtHandler jwtHandler;

  @PostMapping
  @Transactional
  public ResponseEntity<EmployeeDTOToReturn> createEmployee(@RequestBody @Valid EmployeeDTORegister employeeDTORegister)
  {
    EmployeeDTO employeeDTO = employeeDTORegisterMapper.toDto(employeeDTORegister);
    employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
    EmployeeDTO employeeJPAEntity = employeeUseCaseInputPort.createEmployee(employeeDTO);
    EmployeeDTOToReturn employeeDTOToReturn = employeeDTOToReturnMapper.toDto(employeeJPAEntity);
    String location = "/api/v1/employees/" + employeeJPAEntity.getId();
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(location).build().toUri();

    return ResponseEntity.created(uri).body(employeeDTOToReturn);
  }

  @GetMapping("/{id}")
  @Transactional(readOnly = true)
  public ResponseEntity<EmployeeDTOToReturn> getEmployeeById(@PathVariable(name = "id") Long id) {

    EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeById(id);

    EmployeeDTOToReturn employeeDTOToReturn = employeeDTOToReturnMapper.toDto(employeeDTO);
    System.out.println("EmployeeDTOToReturn: " + employeeDTO.getName());

    return ResponseEntity.ok().body(employeeDTOToReturn);
  }

  @GetMapping
  @Transactional(readOnly = true)
  public ResponseEntity<Page<EmployeeDTOToReturn>> getAllEmployees(@RequestParam(required = false ,name = "page") int page, @RequestParam(required = false, name = "size") int size) {

    Pageable pageable = PageRequest.ofSize(size).withPage(page);

    List<EmployeeDTO> employeeDTOList = employeeUseCaseInputPort.listAllEmployees(page, size);
    List<EmployeeDTOToReturn> employeeDTOToReturnList = employeeDTOList.stream().map(employeeDTO -> employeeDTOToReturnMapper.toDto(employeeDTO)).collect(
      Collectors.toList());

    Page<EmployeeDTOToReturn> pageImpl = new PageImpl<>(employeeDTOToReturnList, pageable , 12);

    return ResponseEntity.ok().body(pageImpl);

  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<EmployeeDTOToReturn> updateEmployee(@PathVariable(name = "id") Long id, @RequestBody @Valid EmployeeUpdateDTO employeeUpdateDTO, Authentication authentication) {

    UserDetails userDetails = (EmployeeUserDetailDTO) authentication.getPrincipal();
    EmployeeDTO employee = employeeUseCaseInputPort.getEmployeeByEmail(userDetails.getUsername());
    if (!employee.getId().equals(id)) {
      throw new UnauthorizedException("You can only update your own employee information.");
    }

    EmployeeDTO employeeDTO = employeeUpdateDTOMapper.toDto(employeeUpdateDTO);
    employeeDTO.setId(id);
    EmployeeDTO employeeUpdated = employeeUseCaseInputPort.updateEmployee(employeeDTO);
    EmployeeDTOToReturn employeeDTOToReturn = employeeDTOToReturnMapper.toDto(employeeUpdated);

    return ResponseEntity.ok().body(employeeDTOToReturn);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Object> deleteEmployee(@PathVariable(name = "id") Long id, Authentication authentication){

    UserDetails userDetails = (EmployeeUserDetailDTO) authentication.getPrincipal();
    EmployeeDTO employee = employeeUseCaseInputPort.getEmployeeByEmail(userDetails.getUsername());
    if (!employee.getId().equals(id)) {
      throw new UnauthorizedException("You can only update your own employee information.");
    }

    employeeUseCaseInputPort.deleteEmployee(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/login")
  public ResponseEntity<TokenReturnDTO> authorize(@RequestBody @Valid EmployeeLoginDTO employeeLoginDTO) {
    System.out.println("AQUI");
    var userNamePassword = new UsernamePasswordAuthenticationToken(employeeLoginDTO.getEmail(), employeeLoginDTO.getPassword());
    System.out.println("AQUI");

    var auth = this.authenticationManager.authenticate(userNamePassword);
    var token = jwtHandler.generateToken((EmployeeUserDetailDTO) auth.getPrincipal());
    System.out.println("AQUI");
    return ResponseEntity.ok(new TokenReturnDTO(token));
  }

  @PutMapping("/change-password/{id}")
  public ResponseEntity<Object> changePassword(@PathVariable(name = "id") Long id, @RequestBody @Valid ChangePasswordDTO changePasswordDTO, Authentication authentication) {

    UserDetails userDetails = (EmployeeUserDetailDTO) authentication.getPrincipal();
    EmployeeDTO employee = employeeUseCaseInputPort.getEmployeeByEmail(userDetails.getUsername());
    if (!employee.getId().equals(id)) {
      throw new UnauthorizedException("You can only change your own employee password.");
    }

    EmployeeDTO employeeDTO = employeeUseCaseInputPort.changeEmployeePassword(id, passwordEncoder.encode(changePasswordDTO.getNewPassword()));
    EmployeeDTOToReturn employeeDTOToReturn = employeeDTOToReturnMapper.toDto(employeeDTO);

    return ResponseEntity.ok().body(employeeDTOToReturn);
  }


}
