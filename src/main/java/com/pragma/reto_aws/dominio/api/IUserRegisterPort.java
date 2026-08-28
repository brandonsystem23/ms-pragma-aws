package com.pragma.reto_aws.dominio.api;

import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;
import reactor.core.publisher.Mono;

public interface IUserRegisterPort {

    Mono<User> create(UserRegisterCommand userRegisterCommand);
}
