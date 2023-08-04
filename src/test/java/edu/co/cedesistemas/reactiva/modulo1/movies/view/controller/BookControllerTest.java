package edu.co.cedesistemas.reactiva.modulo1.movies.view.controller;


import edu.co.cedesistemas.reactiva.modulo1.movies.business.service.BookService;
import edu.co.cedesistemas.reactiva.modulo1.movies.model.Book;
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
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookByIdSuccess(){
        int id = 12;
        when(bookService.findById(id)).thenReturn(Mono.just(getBook(id)));
        Mono<Book> response = bookController.getBookById(id);
        assertEquals(getBook(id), response.block());
    }

    @Test
    void testGetBookByIdNotFound(){
        int id = 13;
        when(bookService.findById(id)).thenReturn(Mono.empty());
        when(bookService.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        assertThrows(ResponseStatusException.class, () -> bookController.getBookById(id).block());
    }

    @Test
    void testGetAllBooksSuccess(){
        Book book1 = getBook(11);
        Book book2 = getBook(12);
        Flux<Book> expectedBooks = Flux.just(book1, book2);
        when(bookService.findAll()).thenReturn(expectedBooks);
        Flux<Book> response = bookController.getAllBooks();
        response.subscribe();
        assertEquals(expectedBooks, response);
    }

    @Test
    void testSearchBookSuccess(){
        Book expectedBook = getBook(11);
        Book book1 = getBook(11);
        Book book2 = getBook(12);
        Flux<Book> expectedBooks = Flux.just(book1, book2);
        when(bookService.search(expectedBook)).thenReturn(expectedBooks);
        Flux<Book> response = bookController.searchBook(expectedBook);
        response.subscribe();
        assertEquals(expectedBooks, response);
    }

    @Test
    void testSaveBookSuccess(){
        Book expectedBook = getBook(12);
        when(bookService.save(any(Book.class))).thenReturn(Mono.just(expectedBook));

        Mono<Book> response = bookController.saveBook(expectedBook);
        StepVerifier.create(response)
                .expectNext(expectedBook)
                .verifyComplete();
    }

    @Test
    void testSaveBookError(){
        Book expectedBook = getBook(12);
        when(bookService.save(any(Book.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not save")));

        StepVerifier.create(bookController.saveBook(expectedBook))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testUpdateBookSuccess(){
        Book bookUpdated = getBook(12);
        when(bookService.update(any(Integer.class), any(Book.class))).thenReturn(Mono.just(bookUpdated));

        Mono<Book> response = bookController.updateBook(bookUpdated.getId(), bookUpdated);

        StepVerifier.create(response)
                .expectNext(bookUpdated)
                .verifyComplete();
    }

    @Test
    void testUpdateBookError(){
        Book bookUpdated = getBook(12);
        when(bookService.update(any(Integer.class), any(Book.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "book not update")));

        StepVerifier.create(bookController.updateBook(bookUpdated.getId(), bookUpdated))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void testDeleteBookSuccess(){
        int id = 100;
        Book deletedBook = getBook(id);
        when(bookService.deleteById(any())).thenReturn(Mono.just(deletedBook));
        Mono<Book> response = bookController.deleteBook(id);
        assertEquals(deletedBook, response.block());
    }

    @Test
    void testDeleteBookError(){
        int id = 100;
        when(bookService.deleteById(any())).thenReturn(Mono.empty());
        when(bookService.deleteById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "book not deleted"));
        assertThrows(ResponseStatusException.class, () -> bookController.deleteBook(id).block());
    }

    private Book getBook(Integer id){
        Book book = new Book();
        book.setId(id);
        book.setTitle("Doce cuentos peregrinos");
        book.setEditionDate(LocalDate.of(1992, 12, 12));
        book.setEditorial("Penguin Random House");
        book.setAuthors("Gabriel García Márquez");
        book.setIsbn("1236578");
        book.setReaded(Boolean.FALSE);
        book.setTimeReaded(61);

        return book;
    }
}