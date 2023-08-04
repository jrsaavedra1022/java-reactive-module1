package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.ChapterRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Chapter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ChapterService {

    ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository){
        this.chapterRepository = chapterRepository;
    }

    public Mono<Chapter> findById(Integer id){
        return chapterRepository.findById(id);
    }

    public Flux<Chapter> findAll(){
        return chapterRepository.findAll();
    }

    public Mono<Chapter> save(Chapter chapter){
        return chapterRepository.save(chapter);
    }

    public Mono<Chapter> update(Integer id, Chapter chapter){
        return chapterRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(currentChapter -> {
                    chapter.setId(id);
                    return chapterRepository.save(chapter);
                });
    }

    public Mono<Chapter> deleteById(Integer id){
        return chapterRepository.findById(id)
                .flatMap(currentChapter -> chapterRepository.deleteById(currentChapter.getId())
                        .thenReturn(currentChapter));
    }
}
