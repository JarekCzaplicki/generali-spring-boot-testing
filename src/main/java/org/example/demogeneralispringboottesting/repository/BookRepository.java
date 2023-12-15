package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(String genre);

    List<Book> findBooksByAuthorAndPublishedYear(String author, int publishedYear);

    int countBooksByPublishedYear(int year);

    Optional<Book> findBookByIsbn(String isbn);

    @Query(value = "SELECT * FROM books WHERE price < ?1", nativeQuery = true)
    List<Book> findBooksCheaperThan(BigDecimal price);
    List<Book> findByPriceLessThan(BigDecimal price);
    List<Book> findAllByPriceBefore(BigDecimal price);

    @Query(value = "SELECT * FROM books WHERE available = true", nativeQuery = true)
    List<Book> findAllAvailableBooks();

    List<Book> findBooksByAvailableTrue();

    List<Book> findByAuthor(String author);

    @Query(value = "SELECT * FROM books WHERE published_year >:year AND genre =:genre", nativeQuery = true)
    List<Book> findRecentBooksByGenre(int year, String genre);

    List<Book> findAllByPublishedYearAfterAndGenre(int year, String genre);

    long countByGenre(String genre);
}
