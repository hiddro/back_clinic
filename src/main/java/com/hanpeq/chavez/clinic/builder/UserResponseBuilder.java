package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.PasswordResponse;
import com.hanpeq.chavez.clinic.dto.RolDetails;
import com.hanpeq.chavez.clinic.dto.UserDetails;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .roles(userPrincipal.getRoles() == null ? new ArrayList<>() : buildRolDetails(userPrincipal.getRoles()))
                .details(userPrincipal.getDetails() == null ? null : userPrincipal.getDetails())
                .build();
    }

    public List<RolDetails> buildRolDetails(List<RolePrinciṕal> roles){
        return roles.stream().map(rol -> RolDetails.builder()
                .id(rol.getId())
                .name(Commons.validateNameRol(rol.getName()))
                .build())
                .collect(Collectors.toList());
    }

    public PasswordResponse buildPasswordResponse(UserPrincipal userPrincipal){
        return PasswordResponse.builder()
                .validate("true")
                .build();
    }
}
