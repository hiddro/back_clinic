package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.RolRequest;
import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RoleRequestBuilder {

    public RolePrinciṕal buildOfRoleRequest(RolRequest rolRequest){
        return RolePrinciṕal.builder()
                .name(rolRequest.getName().toString())
                .build();
    }

    public RolePrinciṕal buildOfString(String name){
        return RolePrinciṕal.builder()
                .name(name)
                .build();
    }
}
