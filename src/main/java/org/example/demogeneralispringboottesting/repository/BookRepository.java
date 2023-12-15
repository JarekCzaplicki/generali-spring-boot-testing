package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(String genre);

    List<Book> findBooksByAuthorAndPublishedYear(String author, int publishedYear);
}