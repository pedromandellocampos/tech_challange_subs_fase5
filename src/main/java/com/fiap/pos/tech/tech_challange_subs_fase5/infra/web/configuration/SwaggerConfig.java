package com.fiap.pos.tech.tech_challange_subs_fase5.infra.web.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Tech Challenge API")
        .version("v1")
        .description("API protegida por JWT puro (sem prefixo 'Bearer')"))
      .components(new Components()
        .addSecuritySchemes("jwtToken",
          new SecurityScheme()
            .name("Authorization")
            .description("Insira apenas o JWT (sem 'Bearer')")
            .in(SecurityScheme.In.HEADER)
            .type(SecurityScheme.Type.APIKEY)
            .scheme("apiKey")));
  }

}
