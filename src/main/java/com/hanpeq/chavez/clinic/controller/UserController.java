package com.hanpeq.chavez.clinic.controller;

import com.hanpeq.chavez.clinic.api.v1.UserApi;
import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.service.UserService;
import com.hanpeq.chavez.clinic.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Constants.STRING_CLINIC_PATH)
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public Mono<ResponseEntity<Flux<UserResponse>>> listUsers(ServerWebExchange exchange) {
        return userService.listUsers()
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @Override
    public Mono<ResponseEntity<UserResponse>> registerUser(Mono<UserRequest> userRequest, ServerWebExchange exchange) {
        return userRequest.flatMap(user -> userService.registerUser(user))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @Override
    public Mono<ResponseEntity<UserResponse>> getUserByUsername(String username, ServerWebExchange exchange) {
        return userService.getUserByUsername(username)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }
}
