package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Movie {

    @Id
    private Integer id;
    private String title;
    private String genre;
    private String creator;
    private Integer duration;
    private String releaseYear;
    private Boolean viewed;
    private Integer timeViewed;

}
