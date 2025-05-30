package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.authentication.infra.security.JwtHandler;
import com.fiap.pos.tech.tech_challange_subs_fase5.infra.web.exceptions.UnauthorizedException;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/residents")
@AllArgsConstructor
public class ResidentController {

  private ResidentUpdateDTOMapper residentUpdateDTOMapper;
  private ResidentDTORegisterMapper residentDTORegisterMapper;
  private ResidentDTOToReturnMapper residentDTOToReturnMapper;
  private ResidentUseCaseInputPort residentUseCaseInputPort;
  private AuthenticationManager authenticationManager;
  private PasswordEncoder passwordEncoder;
  private JwtHandler jwtHandler;

  @PostMapping
  @Operation(
      summary = "Create a new resident",
      description = "This endpoint allows you to create a new resident in the system. The password will be encoded before saving."
     , security = @SecurityRequirement(name = "")
  )
  public ResponseEntity<ResidentDTOToReturn> createResident(@RequestBody ResidentDTORegister residentDTORegister) {
    ResidentDTO residentDTO = residentDTORegisterMapper.toEntity(residentDTORegister);
    residentDTO.setPassword(passwordEncoder.encode(residentDTO.getPassword()));
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentUseCaseInputPort.createResident(residentDTO));

    URI uri = URI.create("/api/v1/residents/" + residentDTOToReturn.getId());

    return ResponseEntity.created(uri).body(residentDTOToReturn);
  }

  @PutMapping("/{id}")
  @Transactional
  @Operation(
      summary = "Update resident information",
      description = "This endpoint allows you to update your own resident information. You can only update your own account.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<ResidentDTOToReturn> updateResident(@RequestBody @Valid ResidentUpdateDTO residentUpdateDTO, @PathVariable Long id, Authentication authentication) {

    UserDetails userDetails = (ResidentUserDetailDTO) authentication.getPrincipal();

    var resident = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());
    if (!resident.getId().equals(id)) {
      throw new UnauthorizedException("You can only update your own resident information.");
    }

    ResidentDTO residentDTO = residentUpdateDTOMapper.toDto(residentUpdateDTO);
    residentDTO.setId(id);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentUseCaseInputPort.updateResident(residentDTO));
    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping("/{id}")
  @Transactional(readOnly = true)
  @Operation(
      summary = "Get resident by ID",
      description = "This endpoint retrieves a resident's details by their ID.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<ResidentDTOToReturn> getResident(@PathVariable Long id){
    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentById(id);
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentDTO);

    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping("/my-account")
  @Transactional(readOnly = true)
  @Operation(
      summary = "Get current resident account",
      description = "This endpoint retrieves the current resident's account details.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<ResidentDTOToReturn> getMyAccount(Authentication authentication) {

    UserDetails userDetails = (ResidentUserDetailDTO) authentication.getPrincipal();
    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentDTO);

    return ResponseEntity.ok(residentDTOToReturn);
  }

  @GetMapping()
  @Transactional(readOnly = true)
  @Operation(
      summary = "List all residents",
      description = "This endpoint retrieves a paginated list of all residents.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<List<ResidentDTOToReturn>> getAllResidents(@RequestParam(required = false) int page, @RequestParam(required = false) int size) {
    List<ResidentDTO> residentDTOList = residentUseCaseInputPort.listAllResidents(page, size);
    return ResponseEntity.ok(residentDTOList.stream().map(residentDTOToReturnMapper::toDto).collect(Collectors.toList()));
  }

  @DeleteMapping("/{id}")
  @Transactional
  @Operation(
      summary = "Delete a resident",
      description = "This endpoint allows you to delete your own resident account. You can only delete your own account.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<Object> deleteResident(@PathVariable Long id, Authentication authentication) {

    UserDetails userDetails = (ResidentUserDetailDTO) authentication.getPrincipal();

    var resident = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());
    if (!resident.getId().equals(id)) {
      throw new UnauthorizedException("You can only delete your own resident account.");
    }

    residentUseCaseInputPort.deleteResident(id.longValue());

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/login")
  @Transactional(readOnly = true)
  @Operation(
      summary = "Resident login",
      description = "This endpoint allows a resident to log in and receive a JWT token.",
    security =
    @SecurityRequirement(name = "")
  )
  public ResponseEntity<TokenReturnDTO> login(@RequestBody @Valid ResidentLoginDTO residentLoginDTO) {

    var userNamePassword = new UsernamePasswordAuthenticationToken(residentLoginDTO.getEmail(), residentLoginDTO.getPassword());
    var auth = this.authenticationManager.authenticate(userNamePassword);
    var token = jwtHandler.generateToken((ResidentUserDetailDTO) auth.getPrincipal());

    return ResponseEntity.ok(new TokenReturnDTO(token));
  }

  @PutMapping("/change-password/{id}")
  @Transactional
  @Operation(
      summary = "Change resident password",
      description = "This endpoint allows a resident to change their own password. You can only change your own password.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<Object> changePassword(@PathVariable(name = "id") Long id, @RequestBody @Valid ChangePasswordDTO changePasswordDTO, Authentication authentication) {

    UserDetails userDetails = (ResidentUserDetailDTO) authentication.getPrincipal();

    var resident = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());
    if (!resident.getId().equals(id)) {
      throw new UnauthorizedException("You can only update your own resident information.");
    }
    ResidentDTO residentDTO = residentUseCaseInputPort.changeResidentPassword(id, passwordEncoder.encode(changePasswordDTO.getNewPassword()));
    ResidentDTOToReturn residentDTOToReturn = residentDTOToReturnMapper.toDto(residentDTO);

    return ResponseEntity.ok().body(residentDTOToReturn);
  }

}
