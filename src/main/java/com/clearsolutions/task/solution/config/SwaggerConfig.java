package com.clearsolutions.task.solution.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${server.url}")
    private String serverURL;

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().
                        title("REST API for user")
                        .version("v1")
                        .description("REST API for user")
                        .termsOfService(""))
                .addServersItem(new Server()
                        .url(serverURL)
                        .description(""));
    }
}


