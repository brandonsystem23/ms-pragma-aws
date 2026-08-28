package com.pragma.reto_aws.infrastructure.out.postgres.repository;

import com.pragma.reto_aws.infrastructure.out.postgres.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IUserRepository extends ReactiveCrudRepository<UserEntity, Long> {

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByIdentificationNumber(String identificationNumber);

}
