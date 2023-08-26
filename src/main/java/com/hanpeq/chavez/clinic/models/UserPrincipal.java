package com.hanpeq.chavez.clinic.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanpeq.chavez.clinic.dto.UserDetails;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPrincipal {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String names;

    private String lastNames;

    private String username;

    private String password;

    private String email;

    private String code;

    private String status;

    private List<RolePrinciṕal> roles;

    private UserDetails details;
}
