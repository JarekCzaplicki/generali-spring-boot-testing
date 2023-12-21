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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testSaveBook(){
        // given
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        Book savedBook = bookService.saveBook(book);

        // then
        assertThat(savedBook).isNotNull();
        verify(bookRepository).save(book);
    }
}