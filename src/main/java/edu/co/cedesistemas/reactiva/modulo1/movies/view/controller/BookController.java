package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.BookService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Book;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public Mono<Book> getBookById(@PathVariable Integer id){
        return bookService.findById(id);
    }

    @GetMapping("/")
    public Flux<Book> getAllBooks(){
        return bookService.findAll();
    }

    @PostMapping("/")
    public Mono<Book> saveBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @PostMapping("/search")
    public Flux<Book> searchBook(@RequestBody Book book){

        return bookService.search(book);
    }

    @PutMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable Integer id, @RequestBody Book book){
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public Mono<Book> deleteBook(@PathVariable Integer id){
        return bookService.deleteById(id);
    }

}
