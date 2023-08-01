package com.hanpeq.chavez.clinic.repository;

import com.hanpeq.chavez.clinic.models.UserPrincipal;
import reactor.core.publisher.Mono;

public interface UserRepositories extends GenericRepo<UserPrincipal, String> {
    Mono<UserPrincipal> findOneByUsername(String username);
}
