package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.ChapterService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Chapter;
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
@RequestMapping("/chapters")
public class ChapterController {

    ChapterService chapterService;

    public ChapterController(ChapterService chapterService){
        this.chapterService = chapterService;
    }

    @GetMapping("/{id}")
    public Mono<Chapter> getChapterById(@PathVariable Integer id){
        return chapterService.findById(id);
    }

    @GetMapping("/")
    public Flux<Chapter> getAllChapters(){
        return chapterService.findAll();
    }

    @PostMapping("/")
    public Mono<Chapter> saveChapter(@RequestBody Chapter chapter){
        return chapterService.save(chapter);
    }

    @PutMapping("/{id}")
    public Mono<Chapter> updateChapter(@PathVariable Integer id, @RequestBody Chapter chapter){
        return chapterService.update(id, chapter);
    }

    @DeleteMapping("/{id}")
    public Mono<Chapter> deleteChapter(@PathVariable Integer id){
        return chapterService.deleteById(id);
    }

}
