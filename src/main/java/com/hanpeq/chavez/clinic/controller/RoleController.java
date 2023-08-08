package com.hanpeq.chavez.clinic.controller;

import com.hanpeq.chavez.clinic.api.v1.RoleApi;
import com.hanpeq.chavez.clinic.dto.RolDetails;
import com.hanpeq.chavez.clinic.dto.RolRequest;
import com.hanpeq.chavez.clinic.service.RoleService;
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
public class RoleController implements RoleApi {

    @Autowired
    private RoleService roleService;

    @Override
    public Mono<ResponseEntity<Flux<RolDetails>>> listRoles(ServerWebExchange exchange) {
        return roleService.listRoles()
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @Override
    public Mono<ResponseEntity<RolDetails>> registerRole(Mono<RolRequest> rolRequest, ServerWebExchange exchange) {
        return rolRequest.flatMap(rol -> roleService.registerRole(rol))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }
}
