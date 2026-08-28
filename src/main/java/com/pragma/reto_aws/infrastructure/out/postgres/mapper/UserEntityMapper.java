package com.pragma.reto_aws.infrastructure.out.postgres.mapper;


import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.infrastructure.out.postgres.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity user);


    UserEntity toEntity(User user);

}