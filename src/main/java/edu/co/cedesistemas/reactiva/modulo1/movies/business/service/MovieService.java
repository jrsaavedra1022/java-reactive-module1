package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.MovieRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Movie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class MovieService {

    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Mono<Movie> findById(Integer id){
        return movieRepository.findById(id);
    }

    public Flux<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Mono<Movie> save(Movie movie){
        return movieRepository.save(movie);
    }

    public Mono<Movie> update(Integer id, Movie movie){
        return movieRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(currentMovie -> {
                    movie.setId(id);
                    return movieRepository.save(movie);
                });
    }

    public Mono<Movie> deleteById(Integer id){
        return movieRepository.findById(id)
                .flatMap(currentMovie -> movieRepository.deleteById(currentMovie.getId())
                        .thenReturn(currentMovie));
    }
}
