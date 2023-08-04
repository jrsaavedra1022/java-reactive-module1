package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.SeriesService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Series;
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
class SeriesControllerTest {

    @Mock
    private SeriesService seriesService;

    @InjectMocks
    private SeriesController seriesController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSeriesByIdSuccess(){
        int id = 12;
        when(seriesService.findById(id)).thenReturn(Mono.just(getSeries(id)));
        Mono<Series> response = seriesController.getSeriesById(id);
        assertEquals(getSeries(id), response.block());
    }

    @Test
    void testGetSeriesByIdNotFound(){
        int id = 13;
        when(seriesService.findById(id)).thenReturn(Mono.empty());
        when(seriesService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found"));
        assertThrows(ResponseStatusException.class, () -> seriesController.getSeriesById(id).block());
    }

    @Test
    void testGetAllSeriessSuccess(){
        Series serie1 = getSeries(11);
        Series serie2 = getSeries(12);
        Flux<Series> expectedSeriess = Flux.just(serie1, serie2);
        when(seriesService.findAll()).thenReturn(expectedSeriess);
        Flux<Series> response = seriesController.getAllSeries();
        response.subscribe();
        assertEquals(expectedSeriess, response);
    }

    @Test
    void testSaveSeriesSuccess(){
        Series expectedSeries = getSeries(12);
        when(seriesService.save(any(Series.class))).thenReturn(Mono.just(expectedSeries));

        Mono<Series> response = seriesController.saveSeries(expectedSeries);
        StepVerifier.create(response)
                .expectNext(expectedSeries)
                .verifyComplete();
    }

    @Test
    void testSaveSeriesError(){
        Series expectedSeries = getSeries(12);
        when(seriesService.save(any(Series.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not save")));

        StepVerifier.create(seriesController.saveSeries(expectedSeries))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateSeriesSuccess(){
        Series serieUpdated = getSeries(12);
        when(seriesService.update(any(Integer.class), any(Series.class))).thenReturn(Mono.just(serieUpdated));

        Mono<Series> response = seriesController.updateSeries(serieUpdated.getId(), serieUpdated);

        StepVerifier.create(response)
                .expectNext(serieUpdated)
                .verifyComplete();
    }

    @Test
    void testUpdateSeriesError(){
        Series serieUpdated = getSeries(12);
        when(seriesService.update(any(Integer.class), any(Series.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not update")));

        StepVerifier.create(seriesController.updateSeries(serieUpdated.getId(), serieUpdated))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteSeriesSuccess(){
        int id = 100;
        Series deletedSeries = getSeries(id);
        when(seriesService.deleteById(any())).thenReturn(Mono.just(deletedSeries));
        Mono<Series> response = seriesController.deleteSeries(id);
        assertEquals(deletedSeries, response.block());
    }

    @Test
    void testDeleteSeriesError(){
        int id = 100;
        when(seriesService.deleteById(any())).thenReturn(Mono.empty());
        when(seriesService.deleteById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not deleted"));
        assertThrows(ResponseStatusException.class, () -> seriesController.deleteSeries(id).block());
    }

    private Series getSeries(Integer id){
        Series serie = new Series();
        serie.setId(id);
        serie.setTitle("Stranger Things");
        serie.setGenre("suspenso / ciencia ficción");
        serie.setCreator("Matt Duffer, Ross Duffer, Shawn Levy");
        serie.setDuration(151);
        serie.setReleaseYear("2016");
        serie.setViewed(Boolean.FALSE);
        serie.setSessionQuantity(4);

        return serie;
    }

}