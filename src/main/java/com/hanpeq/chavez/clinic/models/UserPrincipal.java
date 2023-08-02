package com.hanpeq.chavez.clinic.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPrincipal {

    @Id
    private String id;

    private String username;

    private String password;

    private Boolean status;

    private List<RolePrinciṕal> roles;
}
