package com.rentals.house.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

  @Bean
  public OpenAPI swaggerConfig() {
    return new OpenAPI()
      .info(new Info()
        .title("Rentals API")
        .version("1.0")
        .description("Documentation de l'API pour les opérations CRUD sur les rentals."));
  }
}
