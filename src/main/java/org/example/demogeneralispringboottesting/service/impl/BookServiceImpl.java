package org.example.demogeneralispringboottesting.service.impl;

import org.example.demogeneralispringboottesting.model.Book;
import org.example.demogeneralispringboottesting.repository.BookRepository;
import org.example.demogeneralispringboottesting.service.BookService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return Optional.empty();
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public List<Book> findBooksByGenre(String genre) {
        return null;
    }

    @Override
    public List<Book> findBooksByAuthorAndPublishedYear(String author, int publishedYear) {
        return null;
    }

    @Override
    public int countBooksByPublishedYear(int year) {
        return 0;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return null;
    }

    @Override
    public List<Book> findBooksCheaperThan(BigDecimal price) {
        return null;
    }

    @Override
    public List<Book> findAllAvailableBooks() {
        return null;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> findRecentBooksByGenre(int year, String genre) {
        return null;
    }

    @Override
    public long countAllBooks() {
        return 0;
    }

    @Override
    public long countBooksByGenre(String genre) {
        return 0;
    }

    @Override
    public List<Book> findMostExpensiveBooks() {
        return null;
    }

    @Override
    public List<Book> findBooksWithRatingsAbove(int ratingThreshold) {
        return null;
    }

    @Override
    public Double calculateAverageRatingByAuthor(String author) {
        return null;
    }

    @Override
    public Book updateBookAvailability(Long id, boolean newAvailabilityStatus) {
        return null;
    }

    @Override
    public Book addRatingToBook(Long id, int rating) {
        return null;
    }
}
