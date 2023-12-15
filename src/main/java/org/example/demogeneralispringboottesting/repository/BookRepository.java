package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT b FROM Book b ORDER BY b.price DESC") // zwraca wszystko
    List<Book> findMostExpensiveBooks();

    @Query(value = "select * from books b where b.price IN (select max(b2.price) from books b2)"
            , nativeQuery = true)
    List<Book> findMostExpensiveBooksNative();

    @Query("SELECT b FROM Book b WHERE :rating < SOME (SELECT r FROM b.ratings r)")
    List<Book> findBooksWithRatingsAbove(@Param("rating")int ratingThreshold);

    @Query("SELECT b FROM Book b WHERE :rating <= ALL (SELECT r FROM b.ratings r)")
    List<Book> findBooksWithMinimumRatingsAbove(@Param("rating")int ratingThreshold);

    @Query("SELECT AVG(r) FROM Book b JOIN b.ratings r WHERE b.author =:author")
    Double calculateAverageRatingByAuthor(String author);
}
