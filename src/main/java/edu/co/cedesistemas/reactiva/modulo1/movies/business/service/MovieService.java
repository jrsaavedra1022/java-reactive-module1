package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.MovieRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Movie;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
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

    public Mono<Map<String, MessageAttributeValue>> getMessageAttributes(Movie movie){
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

        messageAttributes.put("id",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(movie.getId()).orElse(-301).toString())
                        .withDataType("String")
        );
        messageAttributes.put("title",
                new MessageAttributeValue()
                        .withStringValue(movie.getTitle())
                        .withDataType("String")
        );
        messageAttributes.put("genre",
                new MessageAttributeValue()
                        .withStringValue(movie.getGenre())
                        .withDataType("String")
        );
        messageAttributes.put("creator",
                new MessageAttributeValue()
                        .withStringValue(movie.getCreator())
                        .withDataType("String")
        );
        messageAttributes.put("duration",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(movie.getDuration()).orElse(-301).toString())
                        .withDataType("String")
        );
        messageAttributes.put("releaseYear",
                new MessageAttributeValue()
                        .withStringValue(movie.getReleaseYear())
                        .withDataType("String")
        );
        messageAttributes.put("viewed",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(movie.getViewed()).orElse(Boolean.FALSE).toString())
                        .withDataType("String")
        );
        messageAttributes.put("timeViewed",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(movie.getTimeViewed()).orElse(-301).toString())
                        .withDataType("String")
        );

        return Mono.just(messageAttributes);
    }
}
