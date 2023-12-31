package com.hanpeq.chavez.clinic.service.impl;

import com.hanpeq.chavez.clinic.builder.RoleRequestBuilder;
import com.hanpeq.chavez.clinic.builder.RoleResponseBuilder;
import com.hanpeq.chavez.clinic.builder.UserRequestBuilder;
import com.hanpeq.chavez.clinic.builder.UserResponseBuilder;
import com.hanpeq.chavez.clinic.dto.PasswordResponse;
import com.hanpeq.chavez.clinic.dto.UserRequest;
import com.hanpeq.chavez.clinic.dto.UserResponse;
import com.hanpeq.chavez.clinic.dto.UserUpdateRequest;
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

    @Autowired
    private UserRequestBuilder userRequestBuilder;

    @Autowired
    private UserResponseBuilder userResponseBuilder;

    @Autowired
    private RoleRequestBuilder roleRequestBuilder;

    @Autowired
    private RoleResponseBuilder roleResponseBuilder;

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
                .switchIfEmpty(Mono.error(new BadCredentialsException("Bad Credentials")));
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
                .switchIfEmpty(roleRepositories.findOneByName("USER")
                        .flatMap(rol -> userRepositories.save(userRequestBuilder.buildOfUserRequest(userRequest, rol)))
                        .map(userResponseBuilder::buildOfUserPrincipal));
    }

    @Override
    public Mono<Flux<UserResponse>> listUsers() {
        return Mono.just(userRepositories.findAll()
                .map(userResponseBuilder::buildOfUserPrincipal));
    }

    @Override
    public Mono<UserResponse> getUserByUsername(String username) {
        return userRepositories.findOneByUsername(username)
                .map(userResponseBuilder::buildOfUserPrincipal)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parámetro incorrecto.")));
    }

    @Override
    public Mono<UserResponse> assingRolToUser(String rol, String username) {
        return userRepositories.findOneByUsername(username)
                .filter(user -> !Commons.validateArrayRol(user.getRoles(),  rol))
                .flatMap(u -> roleRepositories.findOneByName(rol)
                        .flatMap(r -> {
                            u.getRoles().add(r);
                            return userRepositories.save(u);
                        })
                        .map(userResponseBuilder::buildOfUserPrincipal))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametro Incorrecto Role/Username.")));
    }

    @Override
    public Mono<PasswordResponse> resetPassword(String email, String password) {
        return userRepositories.findOneByEmail(email)
                .flatMap(user -> userRepositories.save(userRequestBuilder.buildPassword(user, password))
                .map(userResponseBuilder::buildPasswordResponse))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametro email incorrecto.")));
    }

    @Override
    public Mono<UserResponse> updateUser(String username, UserUpdateRequest userUpdateRequest) {
        return userRepositories.findOneByUsername(username)
                .flatMap(user -> userRepositories.save(userRequestBuilder.buildUserPrincipalUpdate(user, userUpdateRequest)))
                .map(userResponseBuilder::buildOfUserPrincipal)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametro Incorrecto Username.")));
    }
}
