package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateMailDTO {

  @NotNull
  private Long residentAcknowledgedById;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of birth should be in the format dd/MM/yyyy")
  private String acknowledgmentTimestamp;
  @NotNull
  private boolean receivedByResident;
}
