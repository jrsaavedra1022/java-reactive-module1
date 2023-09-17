package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.Data;

@Data
public class UserAuth {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private boolean active;

}
