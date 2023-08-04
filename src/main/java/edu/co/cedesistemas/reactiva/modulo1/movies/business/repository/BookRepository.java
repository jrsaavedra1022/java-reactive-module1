package edu.co.cedesistemas.reactiva.modulo1.movies.business.repository;

import edu.co.cedesistemas.reactiva.modulo1.movies.model.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends R2dbcRepository<Book, Integer> {

    Flux<Book> findByTitleContaining(String title);

}
