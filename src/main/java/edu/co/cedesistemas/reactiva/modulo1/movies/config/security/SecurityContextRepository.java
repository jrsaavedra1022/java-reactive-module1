package edu.co.cedesistemas.reactiva.modulo1.movies.config.security;

import edu.co.cedesistemas.reactiva.modulo1.movies.config.security.filter.BearerAuthenticationManager;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private BearerAuthenticationManager bearerAuthenticationManager;

    public SecurityContextRepository(BearerAuthenticationManager bearerAuthenticationManager) {
        this.bearerAuthenticationManager = bearerAuthenticationManager;
    }


    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        bearerAuthenticationManager.setBearer(Boolean.FALSE);
        return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .flatMap(authHeader -> {
                    String authToken = authHeader.substring(7);
                    Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
                    bearerAuthenticationManager.setBearer(Boolean.TRUE);
                    return bearerAuthenticationManager.authenticate(auth).map(SecurityContextImpl::new);
                });
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }
}
