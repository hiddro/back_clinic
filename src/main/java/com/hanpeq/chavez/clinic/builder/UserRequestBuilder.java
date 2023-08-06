package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import com.hanpeq.chavez.clinic.utils.constants.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRequestBuilder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserPrincipal buildOfUserRequest(UserRequest userRequest){
        return UserPrincipal.builder()
                .names(userRequest.getNames())
                .lastNames(userRequest.getLastNames())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .code(Commons.generateCode())
                .status(Constants.STRING_FALSE)
                .build();
    }
}
