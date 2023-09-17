package edu.co.cedesistemas.reactiva.modulo1.movies.model.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
