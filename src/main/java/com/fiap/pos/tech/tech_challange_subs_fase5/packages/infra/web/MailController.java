package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto.*;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.security.dto.ResidentUserDetailDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mail")
@Data
@AllArgsConstructor
public class MailController {

  private final ResidentUseCaseInputPort residentUseCaseInputPort;
  private EmployeeUseCaseInputPort employeeUseCaseInputPort;
  private MailUseCaseInputPort mailUseCaseInputPort;
  private CreteMailDTOToDTOMapper creteMailDTOToDTOMapper;
  private UpdateMailDTOToDTOMapper updateMailDTOToDTOMapper;

  @PostMapping
  @Transactional
  @Operation(
      summary = "Register a new mail",
      description = "This endpoint allows you to register a new mail in the system. The employee recipient is determined by the authenticated user.",
    security =
    @SecurityRequirement(name = "jwtToken"))
  public ResponseEntity<MailDTO> registerMail(@RequestBody CreateMailDTO mailDTO, Authentication authentication) {

    UserDetails userDetails = (EmployeeUserDetailDTO) authentication.getPrincipal();
    MailDTO mailUseCaseDTO = creteMailDTOToDTOMapper.toDto(mailDTO);
    EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail(userDetails.getUsername());
    mailUseCaseDTO.setEmployeeRecipientId(employeeDTO.getId());
    MailDTO mailDTOToReturn = mailUseCaseInputPort.registerMail(mailUseCaseDTO);

    return ResponseEntity.ok(mailDTOToReturn);
  }

  @GetMapping("/{id}")
  @Transactional(readOnly = true)
  @Operation(
      summary = "Get mail by ID",
      description = "This endpoint retrieves a mail by its ID.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<MailDTO> getMailById(@PathVariable Long id) {
    MailDTO mailDTO = mailUseCaseInputPort.getMailById(id);
    return ResponseEntity.ok(mailDTO);
  }

  @PutMapping("/{id}")
  @Transactional
  @Operation(
      summary = "Update mail information",
      description = "This endpoint allows you to update the information of an existing mail.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<MailDTO> updateMail(@RequestBody @Valid UpdateMailDTO updateMailDTO, @PathVariable Long id) {

    MailDTO mailDTO = updateMailDTOToDTOMapper.toDto(updateMailDTO);
    mailDTO.setId(id);
    MailDTO mailDTOToReturn = mailUseCaseInputPort.receiveMail(mailDTO);
    return ResponseEntity.ok(mailDTOToReturn);
  }

  @DeleteMapping("/{id}")
  @Transactional
  @Operation(
      summary = "Delete a mail",
      description = "This endpoint allows you to delete a mail by its ID.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<MailDTO> deleteMail(@PathVariable Long id) {
    mailUseCaseInputPort.deleteMail(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Transactional(readOnly = true)
  @Operation(
      summary = "List all mails",
      description = "This endpoint retrieves a paginated list of all mails.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<List<MailDTO>> listAllMails(@RequestParam Integer page, @RequestParam Integer size) {
    List<MailDTO> mailDTOList = mailUseCaseInputPort.listAllMails(page, size);
    return ResponseEntity.ok(mailDTOList);
  }

  @GetMapping("/list-my-packages")
  @Transactional(readOnly = true)
  @Operation(
      summary = "List my packages",
      description = "This endpoint retrieves a list of packages for the authenticated resident."
    ,
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity<List<MailDTO>> listMyPackages(Authentication authentication) {

    UserDetails userDetails = (ResidentUserDetailDTO) authentication.getPrincipal();
    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());
    List<MailDTO> mailDTOList = mailUseCaseInputPort.getMailByUnity(residentDTO.getApartment());
    return ResponseEntity.ok(mailDTOList);
  }

  @PutMapping("/confirm-notification/{id}")
  @Transactional
  @Operation(
      summary = "Confirm notification for a mail",
      description = "This endpoint allows a resident to confirm the notification of a mail by its ID.",
    security =
    @SecurityRequirement(name = "jwtToken")
  )
  public ResponseEntity confirmNotification(@PathVariable Long id, Authentication authentication) {

    UserDetails userDetails = (ResidentUserDetailDTO) authentication.getPrincipal();
    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());

    MailDTO mailDTO = new MailDTO();
    mailDTO.setId(id);
    mailDTO.setResidentConfirmedMailId(residentDTO.getId());
    mailUseCaseInputPort.confirmNotification(mailDTO);

    return ResponseEntity.ok().build();
  }


}
