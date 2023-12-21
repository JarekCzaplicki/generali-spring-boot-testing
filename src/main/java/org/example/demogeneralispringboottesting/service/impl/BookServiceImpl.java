package org.example.demogeneralispringboottesting.service.impl;

import org.example.demogeneralispringboottesting.model.Book;
import org.example.demogeneralispringboottesting.repository.BookRepository;
import org.example.demogeneralispringboottesting.service.BookService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public List<Book> findBooksByAuthorAndPublishedYear(String author, int publishedYear) {
        return bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);
    }

    @Override
    public int countBooksByPublishedYear(int year) {
        return bookRepository.countBooksByPublishedYear(year);
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findBooksCheaperThan(BigDecimal price) {
        return bookRepository.findBooksCheaperThan(price);
    }

    @Override
    public List<Book> findAllAvailableBooks() {
        return bookRepository.findAllAvailableBooks();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findRecentBooksByGenre(int year, String genre) {
        return bookRepository.findRecentBooksByGenre(year, genre);
    }

    @Override
    public long countAllBooks() {
        return bookRepository.count();
    }

    @Override
    public long countBooksByGenre(String genre) {
        return bookRepository.countByGenre(genre);
    }

    @Override
    public List<Book> findMostExpensiveBooks() {
        return bookRepository.findMostExpensiveBooks();
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
