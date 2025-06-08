package com.tracking.number.generator.exception;

import com.mongodb.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex, ServerWebExchange exchange) {
        return Mono.just(ErrorResponse.of(
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Duplicate tracking number",
                exchange.getRequest().getPath().value()
        ));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Mono<ErrorResponse> handleRuntimeException(RuntimeException ex, ServerWebExchange exchange) {
        return Mono.just(ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        ));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGenericException(Exception ex, ServerWebExchange exchange) {
        return Mono.just(ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Unexpected error occurred",
                exchange.getRequest().getPath().value()
        ));
    }
}

