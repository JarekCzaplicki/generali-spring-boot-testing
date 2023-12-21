package org.example.demogeneralispringboottesting.service;

import org.example.demogeneralispringboottesting.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book updateBook(Book book);

    void deleteBook(Long id);

    List<Book> findBooksByGenre(String genre);

    List<Book> findBooksByAuthorAndPublishedYear(String author, int publishedYear);

    int countBooksByPublishedYear(int year);

    Book findBookByIsbn(String isbn);

    List<Book> findBooksCheaperThan(BigDecimal price);

    List<Book> findAllAvailableBooks();

    List<Book> findByAuthor(String author);

    List<Book> findRecentBooksByGenre(int year, String genre);

    long countAllBooks();

    long countBooksByGenre(String genre);

    List<Book> findMostExpensiveBooks();

    List<Book> findBooksWithRatingsAbove(int ratingThreshold);

    Double calculateAverageRatingByAuthor(String author);

    Book updateBookAvailability(Long id, boolean newAvailabilityStatus);

    Book addRatingToBook(Long id, int rating);

}
