package com.hanpeq.chavez.clinic.controller;

import com.hanpeq.chavez.clinic.dto.TokenRequest;
import com.hanpeq.chavez.clinic.dto.TokenResponse;
import com.hanpeq.chavez.clinic.security.utils.JWTUtil;
import com.hanpeq.chavez.clinic.service.TokenService;
import com.hanpeq.chavez.clinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.hanpeq.chavez.clinic.api.v1.TokenApi;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
public class LoginController implements TokenApi {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    public Mono<ResponseEntity<TokenResponse>> getToken(Mono<TokenRequest> tokenRequest, ServerWebExchange exchange) {
        return tokenRequest
                .flatMap(tkn -> userService.searchByUser(tkn.getUsername())
                        .map(usr -> tokenService.buildTokenResponse(tkn.getPassword(), usr)))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }
}
