package com.pragma.reto_aws.application.handler.impl;

import com.pragma.reto_aws.application.dto.request.UserRequest;
import com.pragma.reto_aws.application.dto.response.UserResponse;
import com.pragma.reto_aws.application.handler.IUserHandler;
import com.pragma.reto_aws.application.mapper.UserDtoMapper;
import com.pragma.reto_aws.dominio.api.IUserFindPort;
import com.pragma.reto_aws.dominio.api.IUserRegisterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserRegisterPort iUserRegisterPort;
    private final IUserFindPort iUserFindPort;
    private final UserDtoMapper userDtoMapper;

    @Override
    public Mono<UserResponse> findUser(Long id) {
        return iUserFindPort.findUser(id)
                .map(userDtoMapper::toResponse);
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return iUserRegisterPort.create(userDtoMapper.toDomain(userRequest))
                .map(userDtoMapper::toResponse);
    }
}
