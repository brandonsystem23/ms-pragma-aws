package com.pragma.reto_aws.dominio.spi;

import com.pragma.reto_aws.dominio.model.User;
import reactor.core.publisher.Mono;

public interface IUserPersistencePort {

    Mono<User> save(User user);

    Mono<User> findById(Long id);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByIdentificationNumber(String identificationNumber);
}
