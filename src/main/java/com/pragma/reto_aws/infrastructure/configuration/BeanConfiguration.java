package com.pragma.reto_aws.infrastructure.configuration;

import com.pragma.reto_aws.dominio.api.IUserFindPort;
import com.pragma.reto_aws.dominio.api.IUserRegisterPort;
import com.pragma.reto_aws.dominio.spi.IUserPersistencePort;
import com.pragma.reto_aws.dominio.usecase.FindUserUseCase;
import com.pragma.reto_aws.dominio.usecase.RegisterUserUseCase;
import com.pragma.reto_aws.dominio.validation.DomainUserValidator;
import com.pragma.reto_aws.dominio.validation.UserRegistrationValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DomainUserValidator domainUserValidator() {
        return new DomainUserValidator();
    }

    @Bean
    public UserRegistrationValidator userRegistrationValidator(IUserPersistencePort userPersistencePort) {
        return new UserRegistrationValidator(userPersistencePort);
    }

    @Bean
    public IUserFindPort findUserUseCase(IUserPersistencePort iUserPersistencePort) {
        return new FindUserUseCase(iUserPersistencePort);
    }

    @Bean
    public IUserRegisterPort registerUserUseCase(IUserPersistencePort iUserPersistencePort,
                                                 DomainUserValidator domainUserValidator,
                                                 UserRegistrationValidator userRegistrationValidator) {
        return new RegisterUserUseCase(iUserPersistencePort,
                domainUserValidator,
                userRegistrationValidator);
    }

}
