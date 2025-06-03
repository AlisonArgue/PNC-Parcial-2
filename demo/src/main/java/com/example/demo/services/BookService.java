package com.example.demo.services;


import com.example.demo.entities.Book;
import com.example.demo.entities.dto.SaveBookDto;
import com.example.demo.entities.dto.UpdateBookDto;
import com.example.demo.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Transactional
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public void createBook(SaveBookDto bookInfo){
        Book book = new Book();
        book.setTitle(bookInfo.getTitle());
        book.setAuthor(bookInfo.getAuthor());
        book.setIsbn(bookInfo.getIsbn());
        book.setPublicationYear(bookInfo.getPublicationYear());
        book.setPages(bookInfo.getPages());
        book.setGenre(bookInfo.getGenre());
        bookRepository.save(book);
    }

    public void deleteBook(UUID id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        } else {
            Book book = optionalBook.get();
            bookRepository.deleteById(book.getId());
        }
    }

    public void updateBook(UpdateBookDto bookInfo) {
        Optional<Book> optionalBook = bookRepository.findById(bookInfo.getId());
        if (optionalBook.isEmpty()){
            throw new RuntimeException("Book not found");
        } else {
            Book book = optionalBook.get();
            book.setTitle(bookInfo.getNewTitle());
            book.setLanguage(bookInfo.getNewLanguage());
            bookRepository.save(book);
        }

    }

    public Book getBook(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            return optionalBook.get();
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    public Optional<Book> getBooksByIsbn(String isbn) {
        return bookRepository.findBooksByIsbn(isbn);
    }

    public List<Book> findBooksByPagesBetween(int minPages, int maxPages) {
       return bookRepository.findBooksByPagesBetween(minPages, maxPages);
    }





}
