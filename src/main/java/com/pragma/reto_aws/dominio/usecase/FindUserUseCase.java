package com.pragma.reto_aws.dominio.usecase;

import com.pragma.reto_aws.dominio.api.IUserFindPort;
import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainErrorMessages;
import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindUserUseCase implements IUserFindPort {

    private final IUserPersistencePort iUserPersistencePort;

    @Override
    public Mono<User> findUser(Long id) {
        return iUserPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(
                        new DomainException(
                                DomainErrorCode.USER_NOT_FOUND,
                                DomainErrorMessages.USER_NOT_FOUND))
                );
    }
}
