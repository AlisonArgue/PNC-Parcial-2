package com.example.demo.controllers;


import com.example.demo.entities.Book;
import com.example.demo.entities.dto.SaveBookDto;
import com.example.demo.entities.dto.UpdateBookDto;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    private Pattern ISBN_REGEX = Pattern.compile("^(97(8|9))?\\d{9}(\\d|X)$");
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/create")
    public ResponseEntity<Void> createBook(@RequestBody SaveBookDto bookInfo) {

        // validate is not null
        if (bookInfo.getTitle() == null || bookInfo.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        //year verification is done
        if(bookInfo.getPublicationYear() < 1900 || bookInfo.getPublicationYear() > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().build();
        }

        if (bookInfo.getAuthor() == null || bookInfo.getAuthor().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        /*
        if (bookInfo.getIsbn() == null || !ISBN_REGEX.matcher(bookInfo.getIsbn()).matches()) {
            return ResponseEntity.badRequest().build();
        }*/

        if(bookInfo.getPages() <= 10){
            return ResponseEntity.badRequest().build();
        }

        bookService.createBook(bookInfo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") UUID id, @RequestBody UpdateBookDto bookInfo) {
        try{
            if (id == null || id.toString().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (bookInfo.getNewTitle() == null || bookInfo.getNewTitle().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }


            Book bookOptional = bookService.getBook(id);
            bookOptional.setTitle(bookInfo.getNewTitle());
            bookOptional.setLanguage(bookInfo.getNewLanguage());
            bookRepository.save(bookOptional);
            return ResponseEntity.ok().build();


        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }


    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Book bookOptional = bookService.getBook(id);

        return ResponseEntity.ok(bookOptional);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }


    @GetMapping("/getByAuthor")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author) {
        if (author == null || author.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Book> books = bookService.getBooksByAuthor(author);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/getByGenre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre") String genre) {
        if (genre == null || genre.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Book> books = bookService.getBooksByGenre(genre);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/getIsbn")
    public ResponseEntity<Book> getBookByIsbn(@RequestParam("isbn") String isbn) {
        if (isbn == null || isbn.isEmpty() ) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Book> bookOptional = bookService.getBooksByIsbn(isbn);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookOptional.get());
    }

    @GetMapping("/getPages")
    public ResponseEntity<List<Book>> getBooksByPages(@RequestParam("min") int min, @RequestParam("max") int max) {


        List<Book> books = bookService.findBooksByPagesBetween(min, max);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }




}
