package com.hanpeq.chavez.clinic.controller;

import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping
    public Mono<ResponseEntity<Flux<UserPrincipal>>> findAll(){
        Flux<UserPrincipal> clientFlux = userService.findAll();

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientFlux)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
