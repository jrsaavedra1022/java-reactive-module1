package edu.co.cedesistemas.reactiva.modulo1.movies.config.security.filter;

import edu.co.cedesistemas.reactiva.modulo1.movies.config.security.JWTUtil;
import edu.co.cedesistemas.reactiva.modulo1.movies.config.security.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BearerAuthenticationManager implements  ReactiveAuthenticationManager {
    private JWTUtil jwtUtil;
    private UserDetailServiceImpl userDetailService;
    private Boolean isBearer;

    public void setBearer(Boolean bearer) {
        isBearer = bearer;
    }

    public BearerAuthenticationManager(JWTUtil jwtUtil, UserDetailServiceImpl userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (isBearer == null || !isBearer){
            UserDetails userDetails = userDetailService.loadUserByUsername(authentication.getName());
            if(!userDetailService.isUserValid(userDetails, authentication)){
                throw new BadCredentialsException("credenciales invalidas");
            }
            return Mono.just(new UsernamePasswordAuthenticationToken(
                    userDetails,
                    authentication.getCredentials(),
                    userDetails.getAuthorities()
            ));
        }
        String jwt = authentication.getCredentials().toString();
        String email = jwtUtil.getUsernameFromToken(jwt);
        return Mono.just(email != null && jwtUtil.validateToken(jwt))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtil.getAllClaimsFromToken(jwt);
                    List<String> rolesMap = new ArrayList<>();
                    return new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                });
    }
}
