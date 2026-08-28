package com.pragma.reto_aws.application.handler.impl;

import com.pragma.reto_aws.application.dto.request.UserRequest;
import com.pragma.reto_aws.application.dto.response.UserResponse;
import com.pragma.reto_aws.application.mapper.UserDtoMapper;
import com.pragma.reto_aws.dominio.api.IUserFindPort;
import com.pragma.reto_aws.dominio.api.IUserRegisterPort;
import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserRegisterPort userRegisterPort;

    @Mock
    private IUserFindPort userFindPort;

    @Mock
    private UserDtoMapper userDtoMapper;

    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        userHandler = new UserHandler(userRegisterPort, userFindPort, userDtoMapper);
    }

    @Test
    void shouldFindUserSuccessfully() {
        User user = User.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userFindPort.findUser(1L)).thenReturn(Mono.just(user));
        when(userDtoMapper.toResponse(user)).thenReturn(response);

        StepVerifier.create(userHandler.findUser(1L))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserRequest request = new UserRequest("123456", "Juan Perez", "juan@mail.com");
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", "juan@mail.com");

        User user = User.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userDtoMapper.toDomain(request)).thenReturn(command);
        when(userRegisterPort.create(command)).thenReturn(Mono.just(user));
        when(userDtoMapper.toResponse(user)).thenReturn(response);

        StepVerifier.create(userHandler.createUser(request))
                .expectNext(response)
                .verifyComplete();
    }
}
