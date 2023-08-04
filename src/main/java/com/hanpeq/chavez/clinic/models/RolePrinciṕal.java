package com.hanpeq.chavez.clinic.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "roles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePrinciṕal {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String name;
}
