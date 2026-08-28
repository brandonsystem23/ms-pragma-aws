package com.pragma.reto_aws.application.dto.response;

import lombok.Builder;

@Builder
public record UserResponse (
        Long id,
        String identificationNumber,
        String fullName,
        String email
){
}
