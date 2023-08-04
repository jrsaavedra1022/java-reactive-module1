package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.MagazineService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Magazine;
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
@RequestMapping("/magazines")
public class MagazineController {

    MagazineService magazineService;

    public MagazineController(MagazineService magazineService){
        this.magazineService = magazineService;
    }

    @GetMapping("/{id}")
    public Mono<Magazine> getMagazineById(@PathVariable Integer id){
        return magazineService.findById(id);
    }

    @GetMapping("/")
    public Flux<Magazine> getAllMagazines(){
        return magazineService.findAll();
    }

    @PostMapping("/")
    public Mono<Magazine> saveMagazine(@RequestBody Magazine magazine){
        return magazineService.save(magazine);
    }

    @PutMapping("/{id}")
    public Mono<Magazine> updateMagazine(@PathVariable Integer id, @RequestBody Magazine magazine){
        return magazineService.update(id, magazine);
    }

    @DeleteMapping("/{id}")
    public Mono<Magazine> deleteMagazine(@PathVariable Integer id){
        return magazineService.deleteById(id);
    }

}
