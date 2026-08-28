package com.pragma.reto_aws.application.mapper;

import com.pragma.reto_aws.application.dto.request.UserRequest;
import com.pragma.reto_aws.application.dto.response.UserResponse;
import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponse toResponse(User user);

    UserRegisterCommand toDomain(UserRequest userRequest);
}
