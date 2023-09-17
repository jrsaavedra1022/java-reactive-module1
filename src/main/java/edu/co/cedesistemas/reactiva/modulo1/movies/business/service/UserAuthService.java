package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.UserAuthRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.UserAuth;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserAuthService {

    UserAuthRepository userAuthRepository;

    public UserAuthService(UserAuthRepository userAuthRepository){
        this.userAuthRepository = userAuthRepository;
    }

    public Mono<UserAuth> save(UserAuth userAuth){
        return userAuthRepository.save(userAuth);
    }

    public Mono<UserAuth> getByUsername(String username){
        return userAuthRepository.findByUsername(username);
    }

    public Mono<UserAuth> getByEmail(String email){
        return userAuthRepository.findByEmail(email);
    }

}
