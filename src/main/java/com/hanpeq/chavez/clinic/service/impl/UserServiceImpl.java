package com.hanpeq.chavez.clinic.service.impl;

import com.hanpeq.chavez.clinic.builder.UserRequestBuilder;
import com.hanpeq.chavez.clinic.builder.UserResponseBuilder;
import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.models.UserPrincipal;
import com.hanpeq.chavez.clinic.repository.GenericRepo;
import com.hanpeq.chavez.clinic.repository.RoleRepositories;
import com.hanpeq.chavez.clinic.repository.UserRepositories;
import com.hanpeq.chavez.clinic.security.models.User;
import com.hanpeq.chavez.clinic.service.UserService;
import com.hanpeq.chavez.clinic.utils.commons.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    @Autowired
    private UserRequestBuilder userRequestBuilder;

    @Autowired
    private UserResponseBuilder userResponseBuilder;

    @Override
    protected GenericRepo<UserPrincipal, String> getRepo() {
        return userRepositories;
    }

    @Override
    public Mono<User> searchByUser(String username) {
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
                .flatMap(u -> Mono.just(new User(u.getUsername(), u.getPassword(), Boolean.parseBoolean(u.getStatus()), roles)))
                .switchIfEmpty(Mono.just(User.builder().build()));
    }

    @Override
    public Mono<UserResponse> registerUser(UserRequest userRequest) {
        return userRepositories.findOneByUsername(userRequest.getUsername())
                .flatMap(user -> {
                    if(user.getId() != null){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de entrada inválido");
                    }
                    return Mono.just(UserResponse.builder().build());
                })
                .switchIfEmpty(userRepositories.save(userRequestBuilder.buildOfUserRequest(userRequest)).map(userResponseBuilder::buildOfUserPrincipal));
    }

    @Override
    public Mono<Flux<UserResponse>> listUsers() {
        return Mono.just(userRepositories.findAll()
                .map(userResponseBuilder::buildOfUserPrincipal));
    }
}
