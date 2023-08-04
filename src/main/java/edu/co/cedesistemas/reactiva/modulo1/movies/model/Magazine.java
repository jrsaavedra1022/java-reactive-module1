package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;


@Data
public class Magazine {

    @Id
    private Integer id;
    private String title;
    private LocalDate editionDate;
    private String editorial;
    private String authors;
}
