package com.example.demo.repositories;

import com.example.demo.entities.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {


    List<Book> findBooksByAuthor(String author);

    List<Book> findBooksByLanguage(String language);

    List<Book> findBooksByGenre(String genre);

    Optional<Book> findBooksByIsbn(String isbn);

    List<Book> findBooksByPagesBetween(int start, int end);


    List<Book> findBooksByIsbn(String isbn, Pageable pageable);
}

