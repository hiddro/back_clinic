package com.hanpeq.chavez.clinic.service.impl;

import com.hanpeq.chavez.clinic.builder.RoleRequestBuilder;
import com.hanpeq.chavez.clinic.builder.RoleResponseBuilder;
import com.hanpeq.chavez.clinic.dto.RolDetails;
import com.hanpeq.chavez.clinic.dto.RolRequest;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.models.RolePrinciṕal;
import com.hanpeq.chavez.clinic.repository.GenericRepo;
import com.hanpeq.chavez.clinic.repository.RoleRepositories;
import com.hanpeq.chavez.clinic.service.RoleService;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoleServiceImpl extends CrudServiceImpl<RolePrinciṕal, String> implements RoleService {

    @Autowired
    private RoleRepositories roleRepositories;

    @Autowired
    private RoleRequestBuilder roleRequestBuilder;

    @Autowired
    private RoleResponseBuilder  roleResponseBuilder;

    @Override
    protected GenericRepo<RolePrinciṕal, String> getRepo() {
        return roleRepositories;
    }

    @Override
    public Mono<RolDetails> registerRole(RolRequest rolRequest) {
        return roleRepositories.findOneByName(rolRequest.getName().getValue())
                .flatMap(role -> {
                    if(role.getId() != null){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de entrada inválido");
                    }
                    return Mono.just(RolDetails.builder().build());
                })
                .switchIfEmpty(Mono.just(rolRequest)
                        .filter(r -> Commons.validateFilter(r.getName().getValue().toString()))
                        .flatMap(rol -> roleRepositories.save(roleRequestBuilder.buildOfRoleRequest(rolRequest)))
                        .map(roleResponseBuilder::buildOfRolePrincipal)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol no esta planteado en el contrato."))));
    }

    @Override
    public Mono<Flux<RolDetails>> listRoles() {
        return null;
    }
}
