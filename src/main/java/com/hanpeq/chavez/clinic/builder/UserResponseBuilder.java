package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.RolDetails;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

@Repository
@AllArgsConstructor
public class UserResponseBuilder {

    public UserResponse buildOfUserPrincipal(UserPrincipal userPrincipal){
        return UserResponse.builder()
                .id(userPrincipal.getId())
                .names(userPrincipal.getNames())
                .lastNames(userPrincipal.getLastNames())
                .username(userPrincipal.getUsername())
                .email(userPrincipal.getEmail())
                .code(userPrincipal.getCode())
                .status(userPrincipal.getStatus())
                .roles(userPrincipal.getRoles() == null ? new ArrayList<>() : Arrays.asList(RolDetails.builder()
                        .id(userPrincipal.getRoles().get(0).getId())
                        .name(Commons.validateNameRol(userPrincipal.getRoles().get(0).getName()))
                        .build()))
                .build();
    }
}
