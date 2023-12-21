package org.example.demogeneralispringboottesting.service.impl;

import org.example.demogeneralispringboottesting.model.Book;
import org.example.demogeneralispringboottesting.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

//@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    //    @Mock
    private BookRepository bookRepository = mock(BookRepository.class);
    //    @InjectMocks
    private BookServiceImpl bookService = new BookServiceImpl(bookRepository);
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(
                1L,
                "Władca piersścieni",
                "J.R.R Tolkien",
                "1234567890123",
                1954,
                "Fantasy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5) // można zmienić
        );
    }

    @Test
    void testSaveBook() {
        // given
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        Book savedBook = bookService.saveBook(book);

        // then
        assertThat(savedBook).isEqualTo(book);
        assertThat(savedBook).isNotNull();
        verify(bookRepository).save(book);
    }

    @Test
    void testGetAllBooks() {
        // given
        given(bookRepository.findAll()).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.getAllBooks();

        // then
        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        // given
        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));

        // when
        Optional<Book> foundBook = bookService.getBookById(book.getId());

        // then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get()).isEqualTo(book);
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void testUpdateBook() {
        // given
        given(bookRepository.save(any(Book.class))).willReturn(book);
        Book savedBook = bookService.saveBook(book);
        savedBook.setTitle("Inny tytuł");

        // when
        Book updatedBook = bookService.updateBook(savedBook);

        // then
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getTitle()).isEqualTo("Inny tytuł");
        verify(bookRepository, times(2)).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        // given
        doNothing().when(bookRepository).deleteById(anyLong());

        // when
        bookService.deleteBook(book.getId());

        // then
        verify(bookRepository).deleteById(book.getId());
    }

    @Test
    void testFindBooksByGenre() {
        // given
        String genre = "Fantasy";
        given(bookRepository.findByGenre(genre)).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findBooksByGenre(genre);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(1); // to samo co linia poniżej
        assertThat(books).hasSize(1);
        verify(bookRepository).findByGenre(genre);
    }

    @Test
    void testFindBooksByAuthorAndPublishedYear() {
        // given
        String author = "J.R.R Tolkien";
        int year = 1954;
        given(bookRepository.findBooksByAuthorAndPublishedYear(author, year))
                .willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findBooksByAuthorAndPublishedYear(author, year);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        verify(bookRepository).findBooksByAuthorAndPublishedYear(author, year);
    }

    @Test
    void testCountBooksByPublishedYear() {
        // given
        int year = 1954;
        given(bookRepository.countBooksByPublishedYear(year)).willReturn(1);

        // when
        int counted = bookService.countBooksByPublishedYear(year);

        // then
        assertThat(counted).isEqualTo(1);
        verify(bookRepository).countBooksByPublishedYear(year);
    }

    @Test
    void testFindBookByIsbnPositivePath() {
        // given
        String isbn = "1234567890123";
        given(bookRepository.findBookByIsbn(isbn)).willReturn(Optional.ofNullable(book));

        // when
        Book foundBook = bookService.findBookByIsbn(isbn);

        // then
        assertThat(foundBook).isEqualTo(book);
        verify(bookRepository).findBookByIsbn(isbn);
    }

    @Test
    void testFindBookByIsbnNegativePath() {
        // given
        String isbn = "1234567890123";
        given(bookRepository.findBookByIsbn(isbn)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(()-> bookService.findBookByIsbn(isbn))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testFindBooksCheaperThan(){
        // given
        BigDecimal price = new BigDecimal("50.00");
        given(bookRepository.findBooksCheaperThan(price)).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findBooksCheaperThan(price);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        verify(bookRepository).findBooksCheaperThan(price);
    }

    @Test
    void testFindAllAvailableBooksPositive(){
        // given
        given(bookRepository.findAllAvailableBooks()).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findAllAvailableBooks();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(Book::getAvailable);
    }

    @Test
    void testFindAllAvailableBooksNegative(){
        // given
        book.setAvailable(false);
        given(bookRepository.findAllAvailableBooks()).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findAllAvailableBooks();

        // then
        assertThat(books).isNotEmpty();
        assertFalse(books.get(0).getAvailable());
    }

    @Test
    void testFindByAuthor(){
        // given
        String author = "J.R.R Tolkien";
        given(bookRepository.findByAuthor(author)).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findByAuthor(author);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        verify(bookRepository).findByAuthor(author);
    }

    @Test
    void testFindRecentBooksByGenre(){
        // given
        String genre = "Fantasy";
        int year = 1900;
        given(bookRepository.findRecentBooksByGenre(year, genre)).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findRecentBooksByGenre(year, genre);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        verify(bookRepository).findRecentBooksByGenre(year, genre);
    }

    @Test
    void testCountAllBooks(){
        // given
        given(bookRepository.count()).willReturn(10L);

        // when
        long books = bookService.countAllBooks();

        // then
        assertEquals(10, books);
        assertThat(books).isEqualTo(10);
        verify(bookRepository).count();
    }

    @Test
    void testCountBooksByGenre(){
        // given
        String genre = "Fantasy";
        given(bookRepository.countByGenre(genre)).willReturn(5L);

        // when
        long books = bookService.countBooksByGenre( genre);

        // then
        assertThat(books).isEqualTo(5);
        verify(bookRepository).countByGenre(genre);
    }
    @Test
    void testFindMostExpensiveBooks(){
        // given
        given(bookRepository.findMostExpensiveBooks()).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.findMostExpensiveBooks();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        verify(bookRepository).findMostExpensiveBooks();
    }
}