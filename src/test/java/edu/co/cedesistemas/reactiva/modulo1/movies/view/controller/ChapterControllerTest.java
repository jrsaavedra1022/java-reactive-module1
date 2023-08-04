package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.ChapterService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Chapter;
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
class ChapterControllerTest {

    @Mock
    private ChapterService chapterService;

    @InjectMocks
    private ChapterController chapterController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChapterByIdSuccess(){
        int id = 12;
        when(chapterService.findById(id)).thenReturn(Mono.just(getChapter(id)));
        Mono<Chapter> response = chapterController.getChapterById(id);
        assertEquals(getChapter(id), response.block());
    }

    @Test
    void testGetChapterByIdNotFound(){
        int id = 13;
        when(chapterService.findById(id)).thenReturn(Mono.empty());
        when(chapterService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found"));
        assertThrows(ResponseStatusException.class, () -> chapterController.getChapterById(id).block());
    }

    @Test
    void testGetAllChaptersSuccess(){
        Chapter chapter1 = getChapter(11);
        Chapter chapter2 = getChapter(12);
        Flux<Chapter> expectedChapters = Flux.just(chapter1, chapter2);
        when(chapterService.findAll()).thenReturn(expectedChapters);
        Flux<Chapter> response = chapterController.getAllChapters();
        response.subscribe();
        assertEquals(expectedChapters, response);
    }

    @Test
    void testSaveChapterSuccess(){
        Chapter expectedChapter = getChapter(12);
        when(chapterService.save(any(Chapter.class))).thenReturn(Mono.just(expectedChapter));

        Mono<Chapter> response = chapterController.saveChapter(expectedChapter);
        StepVerifier.create(response)
                .expectNext(expectedChapter)
                .verifyComplete();
    }

    @Test
    void testSaveChapterError(){
        Chapter expectedChapter = getChapter(12);
        when(chapterService.save(any(Chapter.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not save")));

        StepVerifier.create(chapterController.saveChapter(expectedChapter))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateChapterSuccess(){
        Chapter chapterUpdated = getChapter(12);
        when(chapterService.update(any(Integer.class), any(Chapter.class))).thenReturn(Mono.just(chapterUpdated));

        Mono<Chapter> response = chapterController.updateChapter(chapterUpdated.getId(), chapterUpdated);

        StepVerifier.create(response)
                .expectNext(chapterUpdated)
                .verifyComplete();
    }

    @Test
    void testUpdateChapterError(){
        Chapter chapterUpdated = getChapter(12);
        when(chapterService.update(any(Integer.class), any(Chapter.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not update")));

        StepVerifier.create(chapterController.updateChapter(chapterUpdated.getId(), chapterUpdated))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteChapterSuccess(){
        int id = 100;
        Chapter deletedChapter = getChapter(id);
        when(chapterService.deleteById(any())).thenReturn(Mono.just(deletedChapter));
        Mono<Chapter> response = chapterController.deleteChapter(id);
        assertEquals(deletedChapter, response.block());
    }

    @Test
    void testDeleteChapterError(){
        int id = 100;
        when(chapterService.deleteById(any())).thenReturn(Mono.empty());
        when(chapterService.deleteById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not deleted"));
        assertThrows(ResponseStatusException.class, () -> chapterController.deleteChapter(id).block());
    }

    private Chapter getChapter(Integer id){
        Chapter chapter = new Chapter();
        chapter.setNumber(15);
        chapter.setId(id);
        chapter.setTitle("The Vanishing of Will Byers");
        chapter.setGenre("suspenso / ciencia ficción");
        chapter.setCreator("The Duffer Brothers");
        chapter.setDuration(140);
        chapter.setReleaseYear("2016");
        chapter.setViewed(Boolean.TRUE);
        chapter.setTimeViewed(120);
        chapter.setSessionNumber(1);
        chapter.setSeriesId(2);

        return chapter;
    }

}