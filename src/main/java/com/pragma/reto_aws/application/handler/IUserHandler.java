package com.pragma.reto_aws.application.handler;

import com.pragma.reto_aws.application.dto.request.UserRequest;
import com.pragma.reto_aws.application.dto.response.UserResponse;
import reactor.core.publisher.Mono;

public interface IUserHandler {

    Mono<UserResponse> findUser(Long id);

    Mono<UserResponse> createUser(UserRequest userRequest);
}
