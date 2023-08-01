package com.hanpeq.chavez.clinic.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "roles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePrinciṕal {

    @Id
    private String id;

    private String name;
}
