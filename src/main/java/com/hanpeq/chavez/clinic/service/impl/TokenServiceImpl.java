package com.hanpeq.chavez.clinic.service.impl;

import com.hanpeq.chavez.clinic.builder.TokenResponseBuilder;
import com.hanpeq.chavez.clinic.dto.TokenRequest;
import com.hanpeq.chavez.clinic.dto.TokenResponse;
import com.hanpeq.chavez.clinic.security.models.User;
import com.hanpeq.chavez.clinic.security.utils.JWTUtil;
import com.hanpeq.chavez.clinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private TokenResponseBuilder tokenResponseBuilder;

    @Override
    public TokenResponse getTokenResponse(TokenRequest request, User userDetails) {
        if(BCrypt.checkpw(request.getPassword(), userDetails.getPassword())){
            String token = jwtUtil.generateToken(userDetails);
            String expiration = jwtUtil.getExpirationDateFromTokenString(token);

            return tokenResponseBuilder.builderTokenResponse(token, expiration, request.getUsername());
        }else{
            throw new BadCredentialsException("Bad Credentials");
        }
    }
}
