package com.hanpeq.chavez.clinic.service;

import com.hanpeq.chavez.clinic.dto.RolDetails;
import com.hanpeq.chavez.clinic.dto.RolRequest;
import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService extends CrudService<RolePrinciṕal, String>{

    Mono<RolDetails> registerRole(RolRequest rolRequest);

    Mono<Flux<RolDetails>> listRoles();
}
