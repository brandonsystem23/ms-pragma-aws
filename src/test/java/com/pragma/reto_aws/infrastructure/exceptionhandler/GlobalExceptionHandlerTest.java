package com.pragma.reto_aws.infrastructure.exceptionhandler;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.mock.web.server.MockServerWebExchange;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private ServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();

        MockServerHttpRequest request = MockServerHttpRequest
                .get("/api/v1/users/find?id=1")
                .build();

        exchange = MockServerWebExchange.from(request);
    }

    @Test
    void shouldHandleDomainExceptionWithBadRequest() {
        DomainException exception = new DomainException(
                DomainErrorCode.VALIDATION_ERROR,
                "Error de validación"
        );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleDomainException(exception, exchange);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Bad Request", response.getBody().error());
        assertEquals("Error de validación", response.getBody().message());
        assertEquals("/api/v1/users/find", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
        assertNotNull(response.getBody().details());
        assertTrue(response.getBody().details().isEmpty());
    }

    @Test
    void shouldHandleDomainExceptionWithNotFound() {
        DomainException exception = new DomainException(
                DomainErrorCode.USER_NOT_FOUND,
                "Usuario no encontrado"
        );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleDomainException(exception, exchange);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Usuario no encontrado", response.getBody().message());
        assertEquals("/api/v1/users/find", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
        assertNotNull(response.getBody().details());
        assertTrue(response.getBody().details().isEmpty());
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Argumento inválido");

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleIllegalArgument(exception, exchange);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Bad Request", response.getBody().error());
        assertEquals("Argumento inválido", response.getBody().message());
        assertEquals("/api/v1/users/find", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
        assertNotNull(response.getBody().details());
        assertTrue(response.getBody().details().isEmpty());
    }

    @Test
    void shouldHandleGenericException() {
        Exception exception = new Exception("Error interno");

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleGenericException(exception, exchange);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().status());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals("Ocurrió un error interno en el servidor", response.getBody().message());
        assertEquals("/api/v1/users/find", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
        assertNotNull(response.getBody().details());
        assertTrue(response.getBody().details().isEmpty());
    }

    @Test
    void shouldHandleDomainExceptionWithInternalError() {
        DomainException exception = new DomainException(
                DomainErrorCode.INTERNAL_ERROR,
                "Error interno de dominio"
        );

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleDomainException(exception, exchange);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().status());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals("Error interno de dominio", response.getBody().message());
        assertEquals("/api/v1/users/find", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
        assertNotNull(response.getBody().details());
        assertTrue(response.getBody().details().isEmpty());
    }
}
