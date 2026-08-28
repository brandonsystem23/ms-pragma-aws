package com.pragma.reto_aws.dominio.usecase;

import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import com.pragma.reto_aws.dominio.validation.DomainUserValidator;
import com.pragma.reto_aws.dominio.validation.UserRegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    private DomainUserValidator domainUserValidator;
    private UserRegistrationValidator userRegistrationValidator;
    private RegisterUserUseCase registerUserUseCase;

    @BeforeEach
    void setUp() {
        domainUserValidator = new DomainUserValidator();
        userRegistrationValidator = new UserRegistrationValidator(userPersistencePort);
        registerUserUseCase = new RegisterUserUseCase(
                userPersistencePort,
                domainUserValidator,
                userRegistrationValidator
        );
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", "juan@mail.com");

        User savedUser = User.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userPersistencePort.existsByIdentificationNumber("123456")).thenReturn(Mono.just(false));
        when(userPersistencePort.existsByEmail("juan@mail.com")).thenReturn(Mono.just(false));
        when(userPersistencePort.save(any(User.class))).thenReturn(Mono.just(savedUser));

        StepVerifier.create(registerUserUseCase.create(command))
                .expectNext(savedUser)
                .verifyComplete();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userPersistencePort).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("123456", capturedUser.getIdentificationNumber());
        assertEquals("Juan Perez", capturedUser.getFullName());
        assertEquals("juan@mail.com", capturedUser.getEmail());
    }

    @Test
    void shouldReturnErrorWhenCommandIsInvalid() {
        UserRegisterCommand command = new UserRegisterCommand("", "Juan Perez", "juan@mail.com");

        StepVerifier.create(registerUserUseCase.create(command))
                .expectError(DomainException.class)
                .verify();

        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void shouldReturnErrorWhenDocumentAlreadyExists() {
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", "juan@mail.com");

        when(userPersistencePort.existsByIdentificationNumber("123456")).thenReturn(Mono.just(true));

        StepVerifier.create(registerUserUseCase.create(command))
                .expectError(DomainException.class)
                .verify();

        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() {
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", "juan@mail.com");

        when(userPersistencePort.existsByIdentificationNumber("123456")).thenReturn(Mono.just(false));
        when(userPersistencePort.existsByEmail("juan@mail.com")).thenReturn(Mono.just(true));

        StepVerifier.create(registerUserUseCase.create(command))
                .expectError(DomainException.class)
                .verify();

        verify(userPersistencePort, never()).save(any(User.class));
    }
}
