package com.pragma.reto_aws.dominio.validation;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainErrorMessages;
import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserRegistrationValidator {

    private final IUserPersistencePort userPersistencePort;

    public Mono<Void> validateUserUniqueness(String identificationNumber, String email) {
        return validateDocument(identificationNumber)
                .then(Mono.defer(() -> validateEmail(email)));
    }

    private Mono<Void> validateDocument(String identificationNumber) {
        return userPersistencePort.existsByIdentificationNumber(identificationNumber)
                .flatMap(documentAlreadyExists ->
                        Boolean.TRUE.equals(documentAlreadyExists)
                                ? Mono.error(new DomainException(
                                DomainErrorCode.DUPLICATE_DOCUMENT,
                                DomainErrorMessages.DUPLICATE_DOCUMENT
                        )) : Mono.empty()
                );
    }

    private Mono<Void> validateEmail(String email) {
        return userPersistencePort.existsByEmail(email)
                .flatMap(emailAlreadyExists ->
                        Boolean.TRUE.equals(emailAlreadyExists)
                                ? Mono.error(new DomainException(
                                DomainErrorCode.DUPLICATE_EMAIL,
                                DomainErrorMessages.DUPLICATE_EMAIL
                        )) : Mono.empty()
                );
    }
}
