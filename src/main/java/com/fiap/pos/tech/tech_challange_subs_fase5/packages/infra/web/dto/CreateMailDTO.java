package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Validated
@Schema(
    description = "Data Transfer Object for creating a mail",
    title = "Create Mail DTO")
public class CreateMailDTO {
  @NotBlank(message = "Description cannot be blank")
  @Schema(description = "Description of the mail", example = "Package delivery notification", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;
  @NotBlank(message = "Delivery issuer name cannot be blank")
  @Schema(description = "Name of the person or entity issuing the delivery", example = "Amazon Corp.", requiredMode = Schema.RequiredMode.REQUIRED)
  private String deliveryIssuerName;
  @NotBlank(message = "Delivery timestamp cannot be blank")
  @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Delivery timestamp should be in the format dd/MM/yyyy")
  @Schema(description = "Date of the delivery in the format dd/MM/yyyy", example = "15/10/2023", requiredMode = Schema.RequiredMode.REQUIRED)
  private String deliveryTimestamp;
  @NotBlank(message = "Unity cannot be blank")
  @Pattern(regexp = "\\d+", message = "Unity must contain only numeric digits")
  @Schema(description = "Apartament unity destination of the package", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
  private String unity;
}
