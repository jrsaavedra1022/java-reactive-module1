package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.MagazineService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Magazine;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MagazineControllerTest {

    @Mock
    private MagazineService magazineService;

    @InjectMocks
    private MagazineController magazineController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMagazineByIdSuccess(){
        int id = 12;
        when(magazineService.findById(id)).thenReturn(Mono.just(getMagazine(id)));
        Mono<Magazine> response = magazineController.getMagazineById(id);
        assertEquals(getMagazine(id), response.block());
    }

    @Test
    void testGetMagazineByIdNotFound(){
        int id = 13;
        when(magazineService.findById(id)).thenReturn(Mono.empty());
        when(magazineService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine not found"));
        assertThrows(ResponseStatusException.class, () -> magazineController.getMagazineById(id).block());
    }

    @Test
    void testGetAllMagazinesSuccess(){
        Magazine magazine1 = getMagazine(11);
        Magazine magazine2 = getMagazine(12);
        Flux<Magazine> expectedMagazines = Flux.just(magazine1, magazine2);
        when(magazineService.findAll()).thenReturn(expectedMagazines);
        Flux<Magazine> response = magazineController.getAllMagazines();
        response.subscribe();
        assertEquals(expectedMagazines, response);
    }

    @Test
    void testSaveMagazineSuccess(){
        Magazine expectedMagazine = getMagazine(12);
        when(magazineService.save(any(Magazine.class))).thenReturn(Mono.just(expectedMagazine));

        Mono<Magazine> response = magazineController.saveMagazine(expectedMagazine);
        StepVerifier.create(response)
                .expectNext(expectedMagazine)
                .verifyComplete();
    }

    @Test
    void testSaveMagazineError(){
        Magazine expectedMagazine = getMagazine(12);
        when(magazineService.save(any(Magazine.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine not save")));

        StepVerifier.create(magazineController.saveMagazine(expectedMagazine))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateMagazineSuccess(){
        Magazine magazineUpdated = getMagazine(12);
        when(magazineService.update(any(Integer.class), any(Magazine.class))).thenReturn(Mono.just(magazineUpdated));

        Mono<Magazine> response = magazineController.updateMagazine(magazineUpdated.getId(), magazineUpdated);

        StepVerifier.create(response)
                .expectNext(magazineUpdated)
                .verifyComplete();
    }

    @Test
    void testUpdateMagazineError(){
        Magazine magazineUpdated = getMagazine(12);
        when(magazineService.update(any(Integer.class), any(Magazine.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine not update")));

        StepVerifier.create(magazineController.updateMagazine(magazineUpdated.getId(), magazineUpdated))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteMagazineSuccess(){
        int id = 100;
        Magazine deletedMagazine = getMagazine(id);
        when(magazineService.deleteById(any())).thenReturn(Mono.just(deletedMagazine));
        Mono<Magazine> response = magazineController.deleteMagazine(id);
        assertEquals(deletedMagazine, response.block());
    }

    @Test
    void testDeleteMagazineError(){
        int id = 100;
        when(magazineService.deleteById(any())).thenReturn(Mono.empty());
        when(magazineService.deleteById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine not deleted"));
        assertThrows(ResponseStatusException.class, () -> magazineController.deleteMagazine(id).block());
    }

    private Magazine getMagazine(Integer id){
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setTitle("The New York Times");
        magazine.setEditionDate(LocalDate.of(1851, 9, 18));
        magazine.setEditorial("Editorials");
        magazine.setAuthors("Arthur Sulzberger");

        return magazine;
    }

}