package com.pragma.reto_aws.dominio.validation;

import com.pragma.reto_aws.dominio.exception.DomainErrorCode;
import com.pragma.reto_aws.dominio.exception.DomainErrorMessages;
import com.pragma.reto_aws.dominio.exception.DomainException;
import com.pragma.reto_aws.dominio.model.command.UserRegisterCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainUserValidatorTest {

    private DomainUserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new DomainUserValidator();
    }

    @Test
    void shouldValidateSuccessfullyWhenCommandIsValid() {
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", "juan@mail.com");

        assertDoesNotThrow(() -> validator.validateUserCommand(command));
    }

    @Test
    void shouldThrowWhenFullNameIsBlank() {
        UserRegisterCommand command = new UserRegisterCommand("123456", " ", "juan@mail.com");

        DomainException exception = assertThrows(DomainException.class,
                () -> validator.validateUserCommand(command));

        assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        assertEquals(DomainErrorMessages.FULL_NAME_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowWhenIdentificationNumberIsBlank() {
        UserRegisterCommand command = new UserRegisterCommand(" ", "Juan Perez", "juan@mail.com");

        DomainException exception = assertThrows(DomainException.class,
                () -> validator.validateUserCommand(command));

        assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        assertEquals(DomainErrorMessages.NUMBER_IDENTIFICATION_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowWhenIdentificationNumberIsNotNumeric() {
        UserRegisterCommand command = new UserRegisterCommand("ABC123", "Juan Perez", "juan@mail.com");

        DomainException exception = assertThrows(DomainException.class,
                () -> validator.validateUserCommand(command));

        assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        assertEquals(DomainErrorMessages.DOCUMENT_NUMERIC, exception.getMessage());
    }

    @Test
    void shouldThrowWhenEmailIsBlank() {
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", " ");

        DomainException exception = assertThrows(DomainException.class,
                () -> validator.validateUserCommand(command));

        assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        assertEquals(DomainErrorMessages.EMAIL_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowWhenEmailIsInvalid() {
        UserRegisterCommand command = new UserRegisterCommand("123456", "Juan Perez", "invalid-email");

        DomainException exception = assertThrows(DomainException.class,
                () -> validator.validateUserCommand(command));

        assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        assertEquals(DomainErrorMessages.EMAIL_INVALID, exception.getMessage());
    }
}
