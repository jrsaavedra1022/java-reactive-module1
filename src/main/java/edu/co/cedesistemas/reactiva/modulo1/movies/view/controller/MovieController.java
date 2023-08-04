package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.MovieService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Movie;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class MovieController {

    MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public Mono<Movie> getMovieById(@PathVariable Integer id){
        return movieService.findById(id);
    }

    @GetMapping("/")
    public Flux<Movie> getAllMovies(){
        return movieService.findAll();
    }

    @PostMapping("/")
    public Mono<Movie> saveMovie(@RequestBody Movie movie){
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Mono<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie movie){
        return movieService.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public Mono<Movie> deleteMovie(@PathVariable Integer id){
        return movieService.deleteById(id);
    }

}
