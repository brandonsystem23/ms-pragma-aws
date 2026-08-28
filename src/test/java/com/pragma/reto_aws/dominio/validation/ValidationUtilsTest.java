package com.pragma.reto_aws.dominio.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void shouldReturnTrueWhenValueIsNull() {
        assertTrue(ValidationUtils.isBlank(null));
    }

    @Test
    void shouldReturnTrueWhenValueIsEmpty() {
        assertTrue(ValidationUtils.isBlank(""));
    }

    @Test
    void shouldReturnTrueWhenValueHasOnlySpaces() {
        assertTrue(ValidationUtils.isBlank("   "));
    }

    @Test
    void shouldReturnFalseWhenValueHasText() {
        assertFalse(ValidationUtils.isBlank("pragma"));
    }
}
