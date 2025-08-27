package com.gustavosdaniel.stack.authservice.confg;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Patient Service Auth and User Service")
                        .version("1.0.0")
                        .description("Patient Service API Authentication and Users"));
    }
}
