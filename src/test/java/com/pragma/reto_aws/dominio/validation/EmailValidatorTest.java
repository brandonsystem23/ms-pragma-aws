package com.pragma.reto_aws.dominio.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    void shouldReturnTrueWhenEmailIsValid() {
        assertTrue(EmailValidator.isValid("test@mail.com"));
    }

    @Test
    void shouldReturnTrueWhenEmailHasUppercaseAndSpaces() {
        assertTrue(EmailValidator.isValid("  TEST@mail.com  "));
    }

    @Test
    void shouldReturnFalseWhenEmailIsInvalid() {
        assertFalse(EmailValidator.isValid("invalid-email"));
    }

    @Test
    void shouldReturnTrueWhenEmailIsInvalidMethod() {
        assertTrue(EmailValidator.isInvalid("invalid-email"));
    }

    @Test
    void shouldReturnFalseWhenEmailIsInvalidMethodWithValidEmail() {
        assertFalse(EmailValidator.isInvalid("user@mail.com"));
    }
}
