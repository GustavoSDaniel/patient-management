package com.gustavosdaniel.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             @Value("${auth.service.url}") String authServiceUrl
    ) {
        // Constrói o WebClient para se comunicar com o serviço de autenticação
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {

            // 1. Extrai o token do cabeçalho da requisição
            String token = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            // 2. Verifica se o cabeçalho de autorização existe e começa com "Bearer "
            if (token == null || !token.startsWith("Bearer ")) {
                // Se não existir, retorna 401 Unauthorized e encerra a requisição
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 3. Envia o token para o serviço de autenticação para validação
            return webClient.get()
                    .uri("/api/v1/auth/validate") // Endpoint de validação no serviço de autenticação
                    .header(HttpHeaders.AUTHORIZATION, token) // Envia o mesmo cabeçalho
                    .retrieve() // Executa a chamada
                    .toBodilessEntity() // Espera uma resposta (o corpo não importa, apenas o status de sucesso)
                    .then(chain.filter(exchange)); // Se a chamada for bem-sucedida (status 2xx), a requisição continua para o serviço de destino
        };
    }
}
