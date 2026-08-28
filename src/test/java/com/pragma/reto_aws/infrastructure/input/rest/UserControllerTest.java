package com.pragma.reto_aws.infrastructure.input.rest;

import com.pragma.reto_aws.application.dto.request.UserRequest;
import com.pragma.reto_aws.application.dto.response.UserResponse;
import com.pragma.reto_aws.application.handler.IUserHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IUserHandler userHandler;

    @InjectMocks
    private UserController userController;

    @Test
    void shouldCreateUserSuccessfully() {
        UserRequest request = new UserRequest(
                "123456",
                "Juan Perez",
                "juan@mail.com"
        );

        UserResponse response = UserResponse.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userHandler.createUser(any())).thenReturn(Mono.just(response));

        StepVerifier.create(userController.selfRegisterClient(request))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void shouldRetrieveUserSuccessfully() {
        UserResponse response = UserResponse.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userHandler.findUser(anyLong())).thenReturn(Mono.just(response));

        StepVerifier.create(userController.retrieveUser(1L))
                .expectNext(response)
                .verifyComplete();
    }
}
