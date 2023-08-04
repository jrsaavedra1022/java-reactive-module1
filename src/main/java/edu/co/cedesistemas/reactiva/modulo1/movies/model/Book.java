package edu.co.cedesistemas.reactiva.modulo1.movies.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;


@Data
public class Book {

    @Id
    private Integer id;
    private String title;
    private LocalDate editionDate;
    private String editorial;
    private String authors;
    private String isbn;
    private Boolean readed;
    private Integer timeReaded;
}
