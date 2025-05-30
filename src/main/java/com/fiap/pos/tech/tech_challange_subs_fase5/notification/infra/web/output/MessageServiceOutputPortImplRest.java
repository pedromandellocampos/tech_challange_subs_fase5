package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.web.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.dto.NotificationDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.core.usecases.ports.output.MessageServiceOutputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.web.output.dto.From;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.web.output.dto.MailTrapEmailRequest;
import com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.web.output.dto.To;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableRetry
public class MessageServiceOutputPortImplRest implements MessageServiceOutputPort {

  @Value("${api.rest.mail.from}")
  private String apiEmail;
  @Value("${api.rest.mail.token}")
  private String apiToken;
  @Value("${api.rest.mail.base-url}")
  private String apiUrl;

  @Override
  @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 2))
  public void sendMessageToUser(NotificationDTO message) {

    String url = this.apiUrl;
    String apiToken = this.apiToken;


    MailTrapEmailRequest requestBody = new MailTrapEmailRequest();
    From from = new From();
    from.setEmail("hello@example.com");
    from.setName("Mailtrap Test");
    requestBody.setFrom(from);

    To toList = new To();
    toList.setEmail(message.getEmail());
    requestBody.setTo(List.of(toList));
    requestBody.setSubject("[FIAP POS-TECH] - Nova encomenda para o seu apartamento recebida na portaria!");
    requestBody.setText(message.getMessage());
    requestBody.setCategory("Integration Test");

    // Configura os headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiToken);

    HttpEntity<MailTrapEmailRequest> request = new HttpEntity<>(requestBody, headers);

    // Envia a requisição
    //RestTemplate restTemplate = new RestTemplate();
    //ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    //System.out.println("Status: " + response.getStatusCode());
   // System.out.println("Resposta: " + response.getBody());

  }
}
