package com.pragma.reto_aws.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String identificationNumber;

    private String fullName;

    private String email;

}
