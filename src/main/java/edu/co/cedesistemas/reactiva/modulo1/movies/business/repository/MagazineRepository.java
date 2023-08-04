package edu.co.cedesistemas.reactiva.modulo1.movies.business.repository;

import edu.co.cedesistemas.reactiva.modulo1.movies.model.Magazine;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends R2dbcRepository<Magazine, Integer> {
}
