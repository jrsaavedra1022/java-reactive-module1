package edu.co.cedesistemas.reactiva.modulo1.movies.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwt;
}
