package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.UserAuthService;
import edu.co.cedesistemas.reactiva.modulo1.movies.config.security.JWTUtil;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.UserAuth;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.auth.AuthenticationRequest;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.auth.AuthenticationResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JWTUtil jwtUtil;
    UserAuthService userAuthService;

    private final String INVALID_USER_CREDENTIALS = "Credenciales invalidas";

    public AuthController(UserAuthService userAuthService, JWTUtil jwtUtil) {
       this.userAuthService = userAuthService;
       this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public Mono<AuthenticationResponse> auth(@RequestBody AuthenticationRequest request){
        AtomicReference<UserAuth> userAuth = new AtomicReference<>();;
        userAuthService.getByEmail(request.getEmail()).subscribe(user -> {
            userAuth.set(user);
        }, error -> {
            userAuth.set(null);
        });
        if (userAuth.get() == null)
            return Mono.just(new AuthenticationResponse(INVALID_USER_CREDENTIALS));

        String jwt;
        if(BCrypt.checkpw(request.getPassword(), userAuth.get().getPassword())){
            jwt = jwtUtil.generateToken(userAuth.get());
            return Mono.just(new AuthenticationResponse(jwt));
        } else {
            return Mono.just(new AuthenticationResponse(INVALID_USER_CREDENTIALS));
        }
    }
}
