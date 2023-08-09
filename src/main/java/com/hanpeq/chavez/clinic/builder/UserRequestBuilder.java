package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.UserRequest;
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
        userPrincipal.setPassword(passwordEncoder.encode(password));
        return userPrincipal;
    }
}
