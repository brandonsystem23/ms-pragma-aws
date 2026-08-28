package com.pragma.reto_aws.dominio.validation;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainErrorMessages;
import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;

public class DomainUserValidator {

    public void validateUserCommand(UserRegisterCommand command) {
        if (ValidationUtils.isBlank(command.fullName())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR,
                    DomainErrorMessages.FULL_NAME_REQUIRED);
        }


        if (ValidationUtils.isBlank(command.identificationNumber())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR,
                    DomainErrorMessages.NUMBER_IDENTIFICATION_REQUIRED);
        }

        if (!DocumentValidator.isValid(command.identificationNumber())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DOCUMENT_NUMERIC);
        }

        if (ValidationUtils.isBlank(command.email())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.EMAIL_REQUIRED);
        }

        if (EmailValidator.isInvalid(command.email())) {
            throw new DomainException(
                    DomainErrorCode.VALIDATION_ERROR,
                    DomainErrorMessages.EMAIL_INVALID
            );
        }
    }

}
