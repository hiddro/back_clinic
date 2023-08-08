package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.RolDetails;
import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RoleResponseBuilder {

    public RolDetails buildOfRolePrincipal(RolePrinciṕal rolePrinciṕal){
        return RolDetails.builder()
                .id(rolePrinciṕal.getId())
                .name(Commons.validateNameRol(rolePrinciṕal.getName()))
                .build();
    }
}
