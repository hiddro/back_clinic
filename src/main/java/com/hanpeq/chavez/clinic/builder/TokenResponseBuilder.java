package com.hanpeq.chavez.clinic.builder;

import com.hanpeq.chavez.clinic.dto.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TokenResponseBuilder {

    public TokenResponse builderTokenResponse(String token, String expiration){
        return TokenResponse.builder()
                .token(token)
                .expiration(expiration)
                .build();

    }
}
