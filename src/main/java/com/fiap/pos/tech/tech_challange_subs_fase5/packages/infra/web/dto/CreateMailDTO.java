package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
public class CreateMailDTO {
  @NotBlank(message = "Description cannot be blank")
  private String description;
  @NotBlank(message = "Delivery issuer name cannot be blank")
  private String deliveryIssuerName;
  @NotBlank(message = "Delivery timestamp cannot be blank")
  @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Delivery timestamp should be in the format dd/MM/yyyy")
  private String deliveryTimestamp;
  @NotBlank(message = "Unity cannot be blank")
  private String unity;
}
