package com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.packages.core.usecases.dto.MailDTO;

public interface MailMessageOutputPort {

  void sendNotification(MailDTO mailDTO);

}
