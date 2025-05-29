package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security.JwtHandler;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.ChangePasswordDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/residents")
@AllArgsConstructor
public class ResidentController {

  private final ResidentUpdateDTOMapper residentUpdateDTOMapper;
  ResidentDTORegisterMapper residentDTORegisterMapper;
  ResidentDTOToReturnMapper residentDTOToReturnMapper;
  ResidentUseCaseInputPort residentUseCaseInputPort;
  AuthenticationManager authenticationManager;
  PasswordEncoder passwordEncoder;
  JwtHandler jwtHandler;

  @PostMapping
  public ResponseEntity<ResidentDTOToReturn> createResident(@RequestBody ResidentDTORegister residentDTORegister) {
    ResidentDTO residentDTO = residentDTORegisterMapper.toEntity(residentDTORegister);
    residentDTO.setPassword(passwordEncoder.encode(residentDTO.getPassword()));
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentUseCaseInputPort.createResident(residentDTO));

    URI uri = URI.create("/api/v1/residents/" + residentDTOToReturn.getId());

    return ResponseEntity.created(uri).body(residentDTOToReturn);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResidentDTOToReturn> updateResident(@RequestBody @Valid ResidentUpdateDTO residentUpdateDTO, @PathVariable Long id) {
    ResidentDTO residentDTO = residentUpdateDTOMapper.toDto(residentUpdateDTO);
    residentDTO.setId(id);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentUseCaseInputPort.updateResident(residentDTO));
    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResidentDTOToReturn> getResident(@PathVariable Long id){
    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentById(id);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentDTO);

    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping()
  public ResponseEntity<List<ResidentDTOToReturn>> getAllResidents(@RequestParam(required = false) int page, @RequestParam(required = false) int size) {
    List<ResidentDTO> residentDTOList = residentUseCaseInputPort.listAllResidents(page, size);
    return ResponseEntity.ok(residentDTOList.stream().map(residentDTOToReturnMapper::toDto).collect(Collectors.toList()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteResident(@PathVariable Integer id) {
    residentUseCaseInputPort.deleteResident(id.longValue());

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/login")
  public ResponseEntity<TokenReturnDTO> login(@RequestBody @Valid ResidentLoginDTO residentLoginDTO) {

    var userNamePassword = new UsernamePasswordAuthenticationToken(residentLoginDTO.getEmail(), residentLoginDTO.getPassword());
    var auth = this.authenticationManager.authenticate(userNamePassword);
    var token = jwtHandler.generateToken((ResidentUserDetailDTO) auth.getPrincipal());

    return ResponseEntity.ok(new TokenReturnDTO(token));
  }

  @PutMapping("/change-password/{id}")
  public ResponseEntity<Object> changePassword(@PathVariable(name = "id") Long id, @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {

    ResidentDTO residentDTO = residentUseCaseInputPort.changeResidentPassword(id, passwordEncoder.encode(changePasswordDTO.getNewPassword()));
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentDTO);

    return ResponseEntity.ok().body(residentDTOToReturn);
  }

}
