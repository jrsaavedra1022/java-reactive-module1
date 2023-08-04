package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.MagazineRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Magazine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class MagazineService {

    MagazineRepository magazineRepository;

    public MagazineService(MagazineRepository magazineRepository){
        this.magazineRepository = magazineRepository;
    }

    public Mono<Magazine> findById(Integer id){
        return magazineRepository.findById(id);
    }

    public Flux<Magazine> findAll(){
        return magazineRepository.findAll();
    }

    public Mono<Magazine> save(Magazine magazine){
        return magazineRepository.save(magazine);
    }

    public Mono<Magazine> update(Integer id, Magazine magazine){
        return magazineRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(currentMagazine -> {
                    magazine.setId(id);
                    return magazineRepository.save(magazine);
                });
    }

    public Mono<Magazine> deleteById(Integer id){
        return magazineRepository.findById(id)
                .flatMap(currentMagazine -> magazineRepository.deleteById(currentMagazine.getId())
                        .thenReturn(currentMagazine));
    }
}
