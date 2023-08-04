package edu.co.cedesistemas.reactiva.modulo1.movies.business.repository;

import edu.co.cedesistemas.reactiva.modulo1.movies.model.Chapter;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends R2dbcRepository<Chapter, Integer> {
}
