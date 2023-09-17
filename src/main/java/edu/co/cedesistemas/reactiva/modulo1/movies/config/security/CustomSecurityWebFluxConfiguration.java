package edu.co.cedesistemas.reactiva.modulo1.movies.config.security;

import edu.co.cedesistemas.reactiva.modulo1.movies.config.security.filter.BearerAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class CustomSecurityWebFluxConfiguration {

    private SecurityContextRepository securityContextRepository;
    private BearerAuthenticationManager bearerAuthenticationManager;
    private UserDetailServiceImpl userDetailService;

    public CustomSecurityWebFluxConfiguration(BearerAuthenticationManager bearerAuthenticationManager, SecurityContextRepository securityContextRepository, UserDetailServiceImpl userDetailService) {
        this.bearerAuthenticationManager = bearerAuthenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/admin/**").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/books/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN")
                        .pathMatchers("/auth/**").permitAll()

                )
                .httpBasic(withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
        ;

        serverHttpSecurity
                .authorizeExchange( authorize -> authorize
                        .pathMatchers(HttpMethod.GET, "/magazines/**").authenticated()
                        .anyExchange().permitAll()

                )
                .exceptionHandling(exceptionHandlingSpec ->
                        exceptionHandlingSpec.authenticationEntryPoint((swe, e) ->
                                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                        ).accessDeniedHandler((swe, e) ->
                                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                        )
                )
                .httpBasic(withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authenticationManager(bearerAuthenticationManager)
                .securityContextRepository(securityContextRepository)
        ;
        return serverHttpSecurity.build();
    }

     @Bean
    public MapReactiveUserDetailsService userDetailsService(){
        return this.userDetailService.userDetailsService();
     }

}