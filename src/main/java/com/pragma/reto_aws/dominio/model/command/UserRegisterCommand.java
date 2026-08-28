package com.pragma.reto_aws.dominio.model.command;

public record UserRegisterCommand (
        String identificationNumber,
        String fullName,
        String email
){
}
