package edu.co.cedesistemas.reactiva.modulo1.movies.business.service;

import edu.co.cedesistemas.reactiva.modulo1.movies.business.repository.BookRepository;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Book;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class BookService {
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Mono<Book> findById(Integer id){
        return bookRepository.findById(id);
    }

    public Flux<Book> findAll(){
        return bookRepository.findAll();
    }

    public Mono<Book> save(Book book){
        return bookRepository.save(book);
    }

    public Flux<Book> search(Book book){
        if(book.getTitle()==null){
            return Flux.empty();
        }
        return bookRepository.findByTitleContaining(book.getTitle());
    }

    public Mono<Book> update(Integer id, Book book){
        return bookRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(currentBook -> {
                    book.setId(id);
                    return bookRepository.save(book);
                });
    }

    public Mono<Book> deleteById(Integer id){
        return bookRepository.findById(id)
                .flatMap(currentBook -> bookRepository.deleteById(currentBook.getId())
                .thenReturn(currentBook));
    }

}
