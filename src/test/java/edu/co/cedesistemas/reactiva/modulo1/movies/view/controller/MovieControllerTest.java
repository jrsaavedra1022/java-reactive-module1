package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.MovieService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovieByIdSuccess(){
        int id = 12;
        when(movieService.findById(id)).thenReturn(Mono.just(getMovie(id)));
        Mono<Movie> response = movieController.getMovieById(id);
        assertEquals(getMovie(id), response.block());
    }

    @Test
    void testGetMovieByIdNotFound(){
        int id = 13;
        when(movieService.findById(id)).thenReturn(Mono.empty());
        when(movieService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        assertThrows(ResponseStatusException.class, () -> movieController.getMovieById(id).block());
    }

    @Test
    void testGetAllMoviesSuccess(){
        Movie movie1 = getMovie(11);
        Movie movie2 = getMovie(12);
        Flux<Movie> expectedMovies = Flux.just(movie1, movie2);
        when(movieService.findAll()).thenReturn(expectedMovies);
        Flux<Movie> response = movieController.getAllMovies();
        response.subscribe();
        assertEquals(expectedMovies, response);
    }

    @Test
    void testSaveMovieSuccess(){
        Movie expectedMovie = getMovie(12);
        when(movieService.save(any(Movie.class))).thenReturn(Mono.just(expectedMovie));

        Mono<Movie> response = movieController.saveMovie(expectedMovie);
        StepVerifier.create(response)
                .expectNext(expectedMovie)
                .verifyComplete();
    }

    @Test
    void testSaveMovieError(){
        Movie expectedMovie = getMovie(12);
        when(movieService.save(any(Movie.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not save")));

        StepVerifier.create(movieController.saveMovie(expectedMovie))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateMovieSuccess(){
        Movie movieUpdated = getMovie(12);
        when(movieService.update(any(Integer.class), any(Movie.class))).thenReturn(Mono.just(movieUpdated));

        Mono<Movie> response = movieController.updateMovie(movieUpdated.getId(), movieUpdated);

        StepVerifier.create(response)
                .expectNext(movieUpdated)
                .verifyComplete();
    }

    @Test
    void testUpdateMovieError(){
        Movie movieUpdated = getMovie(12);
        when(movieService.update(any(Integer.class), any(Movie.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not update")));

        StepVerifier.create(movieController.updateMovie(movieUpdated.getId(), movieUpdated))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteMovieSuccess(){
        int id = 100;
        Movie deletedMovie = getMovie(id);
        when(movieService.deleteById(any())).thenReturn(Mono.just(deletedMovie));
        Mono<Movie> response = movieController.deleteMovie(id);
        assertEquals(deletedMovie, response.block());
    }

    @Test
    void testDeleteMovieError(){
        int id = 100;
        when(movieService.deleteById(any())).thenReturn(Mono.empty());
        when(movieService.deleteById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not deleted"));
        assertThrows(ResponseStatusException.class, () -> movieController.deleteMovie(id).block());
    }

    private Movie getMovie(Integer id){
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle("Interestelar");
        movie.setGenre("Ciencia ficción/Aventura");
        movie.setCreator("Christopher Nolan");
        movie.setDuration(134);
        movie.setReleaseYear("2014");
        movie.setViewed(Boolean.FALSE);
        movie.setTimeViewed(0);

        return movie;
    }
}