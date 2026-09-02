package com.pragma.reto_aws.infrastructure.exceptionhandler;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex, ServerWebExchange exchange) {
        HttpStatus status = mapStatus(ex.getCode());
        log.error("DomainException status:{} message:{}", status, ex.getMessage());
        return buildResponse(status, ex.getMessage(), exchange, List.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, ServerWebExchange exchange) {
        log.error("IllegalArgumentException status:{} message:{}", HttpStatus.BAD_REQUEST, ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), exchange, List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, ServerWebExchange exchange) {
        String message = "Ocurrió un error interno en el servidor";
        log.error("Exception status:{} message:{}", HttpStatus.INTERNAL_SERVER_ERROR, message);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                message,
                exchange,
                List.of()
        );
    }

    private HttpStatus mapStatus(DomainErrorCode code) {
        return switch (code) {
            case VALIDATION_ERROR, DUPLICATE_DOCUMENT, DUPLICATE_EMAIL -> HttpStatus.BAD_REQUEST;
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            ServerWebExchange exchange,
            List<String> details
    ) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now(ZoneId.of("America/Lima")))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(exchange.getRequest().getPath().value())
                .details(details)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

}
