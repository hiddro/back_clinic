package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import com.hanpeq.chavez.clinic.utils.constants.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
                .build();
    }
}
