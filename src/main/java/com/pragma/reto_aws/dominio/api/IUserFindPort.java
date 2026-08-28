package com.pragma.reto_aws.dominio.api;

import com.pragma.reto_aws.dominio.model.User;

import reactor.core.publisher.Mono;

public interface IUserFindPort {

    Mono<User> findUser(Long id);
}
