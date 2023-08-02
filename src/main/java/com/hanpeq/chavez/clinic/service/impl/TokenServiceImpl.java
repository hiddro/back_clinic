package com.hanpeq.chavez.clinic.service.impl;

import com.hanpeq.chavez.clinic.builder.TokenResponseBuilder;
import com.hanpeq.chavez.clinic.dto.TokenResponse;
import com.hanpeq.chavez.clinic.security.models.User;
import com.hanpeq.chavez.clinic.security.exceptions.ErrorLogin;
import com.hanpeq.chavez.clinic.security.models.AuthResponse;
import com.hanpeq.chavez.clinic.security.utils.JWTUtil;
import com.hanpeq.chavez.clinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public TokenResponse buildTokenResponse(String passRequest, User userDetails) {
        if(BCrypt.checkpw(passRequest, userDetails.getPassword())){
            String token = jwtUtil.generateToken(userDetails);
            String expiration = jwtUtil.getExpirationDateFromTokenString(token);

            return tokenResponseBuilder.builderTokenResponse(token, expiration);
        }else{
            throw new BadCredentialsException("Bad Credentials");
        }
    }
}
