package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.input.MailUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto.CreateMailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto.CreteMailDTOToDTOMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/mail")
@Data
@AllArgsConstructor
public class MailController {

  MailUseCaseInputPort mailUseCaseInputPort;
  CreteMailDTOToDTOMapper creteMailDTOToDTOMapper;

  @PostMapping
  public ResponseEntity<MailDTO> registerMail(@RequestBody CreateMailDTO mailDTO) {
    MailDTO mailDTOToReturn = mailUseCaseInputPort.registerMail(creteMailDTOToDTOMapper.toDto(mailDTO));
    return ResponseEntity.ok(mailDTOToReturn);
  }

  @PutMapping
  public ResponseEntity<MailDTO> receiveMail(@RequestBody MailDTO mailDTO) {
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

}
