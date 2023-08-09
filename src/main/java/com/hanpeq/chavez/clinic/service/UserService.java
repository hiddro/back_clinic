package com.hanpeq.chavez.clinic.service;

import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.security.models.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends CrudService<UserPrincipal, String> {

    Mono<User> searchByUser(String username);

    Mono<UserResponse> registerUser(UserRequest userRequest);

    Mono<Flux<UserResponse>> listUsers();

    Mono<UserResponse> getUserByUsername(String username);

    Mono<UserResponse> assingRolToUser(String rol, String username);
}
