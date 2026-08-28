package com.pragma.reto_aws.dominio.usecase;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainErrorMessages;
import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.model.User;
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
class FindUserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    private FindUserUseCase findUserUseCase;

    @BeforeEach
    void setUp() {
        findUserUseCase = new FindUserUseCase(userPersistencePort);
    }

    @Test
    void shouldReturnUserWhenUserExists() {
        User user = User.builder()
                .id(1L)
                .identificationNumber("123456")
                .fullName("Juan Perez")
                .email("juan@mail.com")
                .build();

        when(userPersistencePort.findById(1L)).thenReturn(Mono.just(user));

        StepVerifier.create(findUserUseCase.findUser(1L))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenUserDoesNotExist() {
        when(userPersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(findUserUseCase.findUser(1L))
                .expectErrorSatisfies(error -> {
                    DomainException exception = (DomainException) error;
                    assert exception.getCode() == DomainErrorCode.USER_NOT_FOUND;
                    assert exception.getMessage().equals(DomainErrorMessages.USER_NOT_FOUND);
                })
                .verify();
    }
}
