package com.pragma.reto_aws.application.dto.request;

public record UserRequest(
        String identificationNumber,
        String fullName,
        String email
) {
}
