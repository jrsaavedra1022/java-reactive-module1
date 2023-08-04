package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.SeriesService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Series;
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
@RequestMapping("/series")
public class SeriesController {

    SeriesService seriesService;

    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @GetMapping("/{id}")
    public Mono<Series> getSeriesById(@PathVariable Integer id){
        return seriesService.findById(id);
    }

    @GetMapping("/")
    public Flux<Series> getAllSeries(){
        return seriesService.findAll();
    }

    @PostMapping("/")
    public Mono<Series> saveSeries(@RequestBody Series series){
        return seriesService.save(series);
    }

    @PutMapping("/{id}")
    public Mono<Series> updateSeries(@PathVariable Integer id, @RequestBody Series series){
        return seriesService.update(id, series);
    }

    @DeleteMapping("/{id}")
    public Mono<Series> deleteSeries(@PathVariable Integer id){
        return seriesService.deleteById(id);
    }

}
