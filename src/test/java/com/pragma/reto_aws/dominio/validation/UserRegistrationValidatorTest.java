package com.pragma.reto_aws.dominio.validation;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainErrorMessages;
import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRegistrationValidatorTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    private UserRegistrationValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserRegistrationValidator(userPersistencePort);
    }

    @Test
    void shouldCompleteWhenDocumentAndEmailDoNotExist() {
        when(userPersistencePort.existsByIdentificationNumber("123456")).thenReturn(Mono.just(false));
        when(userPersistencePort.existsByEmail("user@mail.com")).thenReturn(Mono.just(false));

        StepVerifier.create(validator.validateUserUniqueness("123456", "user@mail.com"))
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenDocumentAlreadyExists() {
        when(userPersistencePort.existsByIdentificationNumber("123456")).thenReturn(Mono.just(true));

        StepVerifier.create(validator.validateUserUniqueness("123456", "user@mail.com"))
                .expectErrorSatisfies(error -> {
                    DomainException exception = (DomainException) error;
                    assert exception.getCode() == DomainErrorCode.DUPLICATE_DOCUMENT;
                    assert exception.getMessage().equals(DomainErrorMessages.DUPLICATE_DOCUMENT);
                })
                .verify();
    }

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() {
        when(userPersistencePort.existsByIdentificationNumber("123456")).thenReturn(Mono.just(false));
        when(userPersistencePort.existsByEmail("user@mail.com")).thenReturn(Mono.just(true));

        StepVerifier.create(validator.validateUserUniqueness("123456", "user@mail.com"))
                .expectErrorSatisfies(error -> {
                    DomainException exception = (DomainException) error;
                    assert exception.getCode() == DomainErrorCode.DUPLICATE_EMAIL;
                    assert exception.getMessage().equals(DomainErrorMessages.DUPLICATE_EMAIL);
                })
                .verify();
    }
}
