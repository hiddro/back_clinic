package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.dto.UserUpdateRequest;
import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import com.hanpeq.chavez.clinic.utils.constants.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

@Repository
@AllArgsConstructor
public class UserRequestBuilder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserPrincipal buildOfUserRequest(UserRequest userRequest, RolePrinciṕal rolePrinciṕal){
        return UserPrincipal.builder()
                .names(userRequest.getNames())
                .lastNames(userRequest.getLastNames())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .code(Commons.generateCode())
                .status(Constants.STRING_FALSE)
                .roles(rolePrinciṕal == null ? new ArrayList<>() : Arrays.asList(rolePrinciṕal))
                .build();
    }

    public UserPrincipal buildPassword(UserPrincipal userPrincipal, String password){
        return UserPrincipal.builder()
                .id(userPrincipal.getId())
                .names(userPrincipal.getNames())
                .lastNames(userPrincipal.getLastNames())
                .username(userPrincipal.getUsername())
                .password(passwordEncoder.encode(password))
                .email(userPrincipal.getEmail())
                .code(userPrincipal.getCode())
                .status(userPrincipal.getStatus())
                .roles(userPrincipal.getRoles())
                .details(userPrincipal.getDetails())
                .build();
    }

    public UserPrincipal buildUserPrincipalUpdate(UserPrincipal userPrincipal, UserUpdateRequest userUpdateRequest){
        return UserPrincipal.builder()
                .id(userPrincipal.getId())
                .names(userUpdateRequest.getNames())
                .lastNames(userUpdateRequest.getLastNames())
                .username(userPrincipal.getUsername())
                .password(userPrincipal.getPassword())
                .email(userUpdateRequest.getEmail())
                .code(userPrincipal.getCode())
                .status("true")
                .roles(userPrincipal.getRoles())
                .details(userUpdateRequest.getDetails())
                .build();
    }
}
