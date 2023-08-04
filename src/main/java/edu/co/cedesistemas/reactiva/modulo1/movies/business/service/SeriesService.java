package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.SeriesRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Series;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class SeriesService {

    SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    public Mono<Series> findById(Integer id){
        return seriesRepository.findById(id);
    }

    public Flux<Series> findAll(){
        return seriesRepository.findAll();
    }

    public Mono<Series> save(Series series){
        return seriesRepository.save(series);
    }

    public Mono<Series> update(Integer id, Series series){
        return seriesRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(currentSeries -> {
                    series.setId(id);
                    return seriesRepository.save(series);
                });
    }

    public Mono<Series> deleteById(Integer id){
        return seriesRepository.findById(id)
                .flatMap(currentSeries -> seriesRepository.deleteById(currentSeries.getId())
                        .thenReturn(currentSeries));
    }

}
