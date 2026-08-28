package com.pragma.reto_aws.infrastructure.out.postgres.adapter;

import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import com.pragma.reto_aws.infrastructure.out.postgres.entity.UserEntity;
import com.pragma.reto_aws.infrastructure.out.postgres.mapper.UserEntityMapper;
import com.pragma.reto_aws.infrastructure.out.postgres.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistencePort {

    private final IUserRepository iUserRepository;
    private final UserEntityMapper userEntityMapper;


    @Override
    public Mono<Boolean> existsByEmail(String email) {

        log.info("Validar existencia de usuario por email: {}", email);

        return iUserRepository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByIdentificationNumber(String numberDocument) {

        log.info("Validar existencia de usuario por numero de documento: {}", numberDocument);

        return iUserRepository.existsByIdentificationNumber(numberDocument);
    }


    @Override
    public Mono<User> save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);

        log.info("Registrando nuevo usuario. document={}, email={}",
                user.getIdentificationNumber(), user.getEmail());

        return iUserRepository.save(userEntity)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Mono<User> findById(Long id) {

        log.info("Buscando usuario con id={}", id);

        return iUserRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }


}
