package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.ResidentJwtHandler;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.pattern.TokenTagToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/residents")
@AllArgsConstructor
public class ResidentController {

  ResidentDTORegisterMapper residentDTORegisterMapper;
  ResidentDTOToReturnMapper residentDTOToReturnMapper;
  ResidentUseCaseInputPort residentUseCaseInputPort;
  AuthenticationManager authenticationManager;
  ResidentJwtHandler residentJwtHandler;

  @PostMapping
  public ResponseEntity<ResidentDTOToReturn> createResident(@RequestBody ResidentDTORegister residentDTORegister) {

    ResidentDTO residentDTO = residentDTORegisterMapper.toEntity(residentDTORegister);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentUseCaseInputPort.createResident(residentDTO));

    URI uri = URI.create("/api/v1/residents/" + residentDTOToReturn.getId());

    return ResponseEntity.created(uri).body(residentDTOToReturn);
  }

  @PutMapping
  public ResponseEntity<ResidentDTOToReturn> updateResident(@RequestBody ResidentDTORegister residentDTORegister) {

    ResidentDTO residentDTO = residentDTORegisterMapper.toEntity(residentDTORegister);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentUseCaseInputPort.updateResident(residentDTO));

    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResidentDTOToReturn> getResident(@PathVariable Long id){
    ResidentDTO residentDTO = residentUseCaseInputPort.getEmployeeById(id);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentDTO);

    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping("/{page}/{size}")
  public ResponseEntity<List<ResidentDTOToReturn>> getAllResidents(@PathVariable Integer page, @PathVariable Integer size) {
    List<ResidentDTO> residentDTOList = residentUseCaseInputPort.listAllResidents(page, size);

    return ResponseEntity.ok(residentDTOList.stream().map(residentDTOToReturnMapper::toDto).collect(Collectors.toList()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteResident(@PathVariable Integer id) {
    residentUseCaseInputPort.deleteEmployee(id.longValue());

    return ResponseEntity.noContent().build();
  }


  @PostMapping("/login")
  public ResponseEntity<TokenReturnDTO> login(@RequestBody @Valid ResidentLoginDTO residentLoginDTO) {

    System.out.println("AQUI");
    var userNamePassword = new UsernamePasswordAuthenticationToken(residentLoginDTO.getEmail(), residentLoginDTO.getPassword());
    System.out.println("AQUI");
    var auth = this.authenticationManager.authenticate(userNamePassword);
    var token = residentJwtHandler.generateToken((ResidentUserDetailDTO) auth.getPrincipal());
    System.out.println("AQUI");

    return ResponseEntity.ok(new TokenReturnDTO(token));



  }




}
