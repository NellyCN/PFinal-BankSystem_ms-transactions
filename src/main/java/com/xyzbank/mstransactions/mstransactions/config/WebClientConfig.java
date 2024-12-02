package com.xyzbank.mstransactions.mstransactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//Configuración del cliente WebClient para realizar peticiones HTTP a otros microservicios.

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8085") // URL base para ms-accounts
                .build();
    }
}
