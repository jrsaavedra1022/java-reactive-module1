package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Publication {

    @Id
    private Integer Id;
    private String title;
    private LocalDate editionDate;
    private String editorial;
    private String[] authors;

}
