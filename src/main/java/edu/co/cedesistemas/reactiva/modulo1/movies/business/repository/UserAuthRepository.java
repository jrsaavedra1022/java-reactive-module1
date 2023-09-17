package edu.co.cedesistemas.reactiva.modulo1.movies.business.repository;

import edu.co.cedesistemas.reactiva.modulo1.movies.model.UserAuth;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserAuthRepository extends R2dbcRepository<UserAuth, Integer> {

    Mono<UserAuth> findByEmail(String email);
    Mono<UserAuth> findByUsername(String username);
}
