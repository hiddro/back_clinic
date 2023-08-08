package com.hanpeq.chavez.clinic.repository;


import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import reactor.core.publisher.Mono;

public interface RoleRepositories extends GenericRepo<RolePrinciṕal, String>{
    Mono<RolePrinciṕal> findOneByName(String name);
}
