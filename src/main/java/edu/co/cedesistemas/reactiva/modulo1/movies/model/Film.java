package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.Data;

@Data
public abstract class Film {

    private String title;
    private String genre;
    private String creator;
    private Integer duration;
    private Short year;
    private Boolean viewed;
}
