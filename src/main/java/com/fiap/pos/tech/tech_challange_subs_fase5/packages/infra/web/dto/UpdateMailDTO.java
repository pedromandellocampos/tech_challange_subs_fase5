package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
    description = "Data Transfer Object for updating a mail",
    title = "Update Mail DTO")
public class UpdateMailDTO {

  @NotNull
  @Schema(description = "ID of the user that received the package. Previous user list consultatino should display usersId that will be used here.", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long residentAcknowledgedById;
  @Pattern( regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Date of birth should be in the format dd/MM/yyyy")
  @Schema(
      description = "Acknowledgment timestamp of the mail in the format dd/MM/yyyy",
      example = "15/10/2023",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String acknowledgmentTimestamp;
  @NotNull
  @Schema(description = "Indicates if the mail was received by the resident", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  private boolean receivedByResident;
}
