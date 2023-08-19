package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.KafkaConsumerService;
import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.MovieService;
import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.SQSService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Movie;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/movies")
public class MovieController {

    MovieService movieService;
    KafkaConsumerService kafkaConsumerService;

    SQSService sqsService;

    public MovieController(MovieService movieService, KafkaConsumerService kafkaConsumerService, SQSService sqsService){
        this.movieService = movieService;
        this.kafkaConsumerService = kafkaConsumerService;
        this.sqsService = sqsService;
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

    @GetMapping("/kafka-topic/{topic}")
    public Mono<Movie> getMovieFromKafkaTopic(@PathVariable String topic){
        return Mono.just((Movie) kafkaConsumerService.getLastObject(topic, Movie.class));
    }

    @PostMapping("/aws/createQueue")
    public Mono<String> postCreateQueue(@RequestBody Map<String, Object> requestBody){
        return Mono.just(sqsService.createStandardQueue((String) requestBody.get("queueName")));
    }

    @PostMapping("/aws/postMessageQueue/{queueName}")
    public Mono<String> postMessageQueue(@RequestBody Movie movie, @PathVariable String queueName){
        AtomicReference<Map<String, MessageAttributeValue>> attributesRef = new AtomicReference<>();
        movieService.getMessageAttributes(movie).subscribe(
                attributes -> attributesRef.set(attributes),
                error -> System.err.println("Error: " + error.getMessage())
        );

        return Mono.just(sqsService.publishStandardQueueMessage(
                queueName,
                2,
                attributesRef.get(),
                movie.getTitle()
        ));
    }

    @GetMapping("/aws/getMessagesQueue/{queueName}")
    public Flux<Movie> getMessageQueue(@PathVariable String queueName){
        return sqsService.receiveMessages(queueName, 2, 2);
    }

}
