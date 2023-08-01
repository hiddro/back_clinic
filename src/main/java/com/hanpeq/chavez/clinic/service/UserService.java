package com.hanpeq.chavez.clinic.service;

import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.security.models.User;
import reactor.core.publisher.Mono;

public interface UserService extends CrudService<UserPrincipal, String> {

    Mono<User> searchByUser(String username);
}
