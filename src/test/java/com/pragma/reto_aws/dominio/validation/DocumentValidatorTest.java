package com.pragma.reto_aws.dominio.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentValidatorTest {

    @Test
    void shouldReturnTrueWhenDocumentContainsOnlyNumbers() {
        assertTrue(DocumentValidator.isValid("123456789"));
    }

    @Test
    void shouldReturnFalseWhenDocumentContainsLetters() {
        assertFalse(DocumentValidator.isValid("123ABC"));
    }

    @Test
    void shouldReturnFalseWhenDocumentContainsSpaces() {
        assertFalse(DocumentValidator.isValid("123 456"));
    }

    @Test
    void shouldReturnFalseWhenDocumentIsNull() {
        assertFalse(DocumentValidator.isValid(null));
    }

    @Test
    void shouldReturnFalseWhenDocumentIsEmpty() {
        assertFalse(DocumentValidator.isValid(""));
    }
}
