package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Chapter {

    @Id
    private Integer id;
    private Integer number;
    private String title;
    private String genre;
    private String creator;
    private Integer duration;
    private String releaseYear;
    private Boolean viewed;
    private Integer timeViewed;
    private Integer sessionNumber;
    private Integer seriesId;

}
