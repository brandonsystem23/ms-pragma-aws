package com.pragma.reto_aws.infrastructure.out.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "users")
public class UserEntity {

    @Id
    private Long id;

    @Column("full_name")
    private String fullName;

    @Column("identification_number")
    private String identificationNumber;

    private String email;


}
