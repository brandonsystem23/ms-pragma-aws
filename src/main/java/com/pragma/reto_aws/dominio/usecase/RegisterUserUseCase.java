package com.pragma.reto_aws.dominio.usecase;

import com.pragma.reto_aws.dominio.api.IUserRegisterPort;
import com.pragma.reto_aws.dominio.model.User;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import com.pragma.reto_aws.dominio.validation.DomainUserValidator;
import com.pragma.reto_aws.dominio.validation.UserRegistrationValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase implements IUserRegisterPort {

    private final IUserPersistencePort iUserPersistencePort;
    private final DomainUserValidator domainUserValidator;
    private final UserRegistrationValidator userRegistrationValidator;

    @Override
    public Mono<User> create(UserRegisterCommand userRegisterCommand) {
        return Mono.defer(() -> {

            domainUserValidator.validateUserCommand(userRegisterCommand);

            return userRegistrationValidator.validateUserUniqueness(
                            userRegisterCommand.identificationNumber(),
                            userRegisterCommand.email()
                    )
                    .then(Mono.defer(() -> {

                        User user = User.builder()
                                .identificationNumber(userRegisterCommand.identificationNumber())
                                .fullName(userRegisterCommand.fullName())
                                .email(userRegisterCommand.email())
                                .build();

                        return iUserPersistencePort.save(user);
                    }));
        });
    }
}
