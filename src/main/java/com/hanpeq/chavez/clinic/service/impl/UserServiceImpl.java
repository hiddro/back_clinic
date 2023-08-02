package com.hanpeq.chavez.clinic.service.impl;

import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.repository.GenericRepo;
import com.hanpeq.chavez.clinic.repository.RoleRepositories;
import com.hanpeq.chavez.clinic.repository.UserRepositories;
import com.hanpeq.chavez.clinic.security.models.User;
import com.hanpeq.chavez.clinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends CrudServiceImpl<UserPrincipal, String> implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private RoleRepositories roleRepositories;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected GenericRepo<UserPrincipal, String> getRepo() {
        return userRepositories;
    }

    @Override
    public Mono<User> searchByUser(String username) {
//        Mono<UserPrincipal> userMono = userRepositories.findOneByUsername(username);
        List<String> roles = new ArrayList<>();

        return userRepositories.findOneByUsername(username).flatMap(u -> {
                    return Flux.fromIterable(u.getRoles())
                            .flatMap(rol -> {
                                return roleRepositories.findById(rol.getId())
                                        .map(r -> {
                                            roles.add(r.getName());
                                            return r;
                                        });
                            })
                            .collectList()
                            .flatMap(list -> {
                                u.setRoles(list);
                                return Mono.just(u);
                            });
                })
                .flatMap(u -> Mono.just(new User(u.getUsername(), u.getPassword(), u.getStatus(), roles)))
                .switchIfEmpty(Mono.just(User.builder().build()));
    }
}
