package com.pragma.reto_aws.dominio.exception;

public final class DomainErrorMessages {

    public static final String FULL_NAME_REQUIRED = "El campo fullName es obligatorio";
    public static final String NUMBER_IDENTIFICATION_REQUIRED = "El campo identificationNumber es obligatorio";
    public static final String DOCUMENT_NUMERIC = "El campo identificationNumber debe contener únicamente números";
    public static final String EMAIL_REQUIRED = "El campo email es obligatorio";
    public static final String EMAIL_INVALID = "El email no tiene un formato válido";

    public static final String DUPLICATE_DOCUMENT = "El numero de identificationNumber ya está registrado";
    public static final String DUPLICATE_EMAIL = "El email ya está registrado";
    public static final String USER_NOT_FOUND = "Usuario no encontrado";

    private DomainErrorMessages() {
    }
}
