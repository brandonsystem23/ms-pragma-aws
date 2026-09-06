package com.pragma.reto_aws.infrastructure.input.rest;

import com.pragma.reto_aws.application.dto.request.UserRequest;
import com.pragma.reto_aws.application.dto.response.UserResponse;
import com.pragma.reto_aws.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios")
public class UserController {

    private final IUserHandler iUserHandler;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registro de usuario", description = "Permite registrar un nuevo usuario")
    public Mono<UserResponse> selfRegisterClient(@Valid @RequestBody UserRequest request) {

        log.info("Petición para crear usuario");

        return iUserHandler.createUser(request);
    }

    @GetMapping("/find")
    @Operation(summary = "Buscar usuario", description = "Busca un usuario por su id")
    public Mono<UserResponse> retrieveUser(@RequestParam(value = "id") Long userId) {

        log.info("Petición para crear buscar usuario con id={}", userId);

        return iUserHandler.findUser(userId);
    }

    @GetMapping("/example")
    @Operation(summary = "ejemplo GET", description = "Ejemplo de operacion GET en swagger")
    public Mono<String> getExample() {

        return Mono.just("Hola mundo");
    }

}
