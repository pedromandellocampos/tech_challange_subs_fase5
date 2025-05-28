package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.security.dto.EmployeeUserDetailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto.*;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
  private ConfirmedMailDTOToDTOMapper confirmedMailDTOToDTOMapper;

  @PostMapping
  public ResponseEntity<MailDTO> registerMail(@RequestBody CreateMailDTO mailDTO, Authentication authentication) {

    UserDetails userDetails = (EmployeeUserDetailDTO) authentication.getPrincipal();
    MailDTO mailUseCaseDTO = creteMailDTOToDTOMapper.toDto(mailDTO);
    EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail(userDetails.getUsername());
    mailUseCaseDTO.setEmployeeRecipientId(employeeDTO.getId());
    MailDTO mailDTOToReturn = mailUseCaseInputPort.registerMail(mailUseCaseDTO);

    return ResponseEntity.ok(mailDTOToReturn);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MailDTO> getMailById(@PathVariable Long id) {
    MailDTO mailDTO = mailUseCaseInputPort.getMailById(id);
    return ResponseEntity.ok(mailDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MailDTO> updateMail(@RequestBody UpdateMailDTO updateMailDTO, @PathVariable Long id) {
    MailDTO mailDTO = updateMailDTOToDTOMapper.toDto(updateMailDTO);
    mailDTO.setId(id);
    MailDTO mailDTOToReturn = mailUseCaseInputPort.receiveMail(mailDTO);
    return ResponseEntity.ok(mailDTOToReturn);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MailDTO> deleteMail(@PathVariable Long id) {
    mailUseCaseInputPort.deleteMail(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<MailDTO>> listAllMails(@RequestParam Integer page, @RequestParam Integer size) {
    List<MailDTO> mailDTOList = mailUseCaseInputPort.listAllMails(page, size);
    return ResponseEntity.ok(mailDTOList);
  }

  @GetMapping("/list-my-packages")
  public ResponseEntity<List<MailDTO>> listMyPackages(Authentication authentication) {
    UserDetails userDetails = (EmployeeUserDetailDTO) authentication.getPrincipal();
    ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail(userDetails.getUsername());
    List<MailDTO> mailDTOList = mailUseCaseInputPort.getMailByUnity(residentDTO.getApartment());
    return ResponseEntity.ok(mailDTOList);
  }

  @PutMapping("/confirm-notification/{id}")
  public ResponseEntity confirmNotification(@PathVariable Long id, @RequestBody ConfirmedMailDTO confirmedMailDTO) {

    MailDTO mailDTO = confirmedMailDTOToDTOMapper.toDTO(confirmedMailDTO);
    mailDTO.setId(id);
    mailUseCaseInputPort.confirmNotification(mailDTO);

    return ResponseEntity.ok().build();
  }


}
