package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.TokenResponse;
import com.hanpeq.chavez.clinic.utils.constants.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TokenResponseBuilder {

    public TokenResponse builderTokenResponse(String token, String expiration, String username){
        return TokenResponse.builder()
                .token(token)
                .type(Constants.STRING_ACCESS_TOKEN)
                .username(username)
                .authenticated(Constants.STRING_TRUE)
                .expiration(expiration)
                .build();

    }
}
