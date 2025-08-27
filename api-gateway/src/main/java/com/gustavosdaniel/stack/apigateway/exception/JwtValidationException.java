package com.gustavosdaniel.stack.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class JwtValidationException {

    @ExceptionHandler(WebClientResponseException.Unauthorized.class)
    public Mono<Void> handleUnauthorized(ServerWebExchange serverWebExchange) {

        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return serverWebExchange.getResponse().setComplete();

    }
}

// Quando a exceção é lançada,
// garante que a resposta enviada ao cliente seja um HTTP 401 Unauthorized,
// sem a necessidade de o desenvolvedor repetir o código de tratamento de erro em cada endpoint