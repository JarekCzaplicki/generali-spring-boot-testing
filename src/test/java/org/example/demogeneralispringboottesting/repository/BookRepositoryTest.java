package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book(
                1L,
                "Władca piersścieni",
                "J.R.R Tolkien",
                "1234567890123",
                1954,
                "Fantasy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5)
        );
    }
    @DisplayName("Test zapisywania książki")
    @Test
    void testSavedBook(){ // przemyśleć czemu nie przechodzi jako część wszystkich testów
        // given
        // when
        Book savedBook = bookRepository.save(book);

        // then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook).isEqualTo(book);
//        assertThat(savedBook).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(book);
        assertEquals(book, savedBook);
    }
    @DisplayName("Test wyszukiwania po Id")
    @Test
    void findById(){
        // given
        Book savedBook = bookRepository.save(book);
        // when
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        //then
        assertThat(foundBook).isPresent();
        assertThat(foundBook).isEqualTo(Optional.of(savedBook));
    }
    @DisplayName("Test aktualizacji danych książki")
    @Test
    void testUpdateBook(){
        // given
        Book savedBook = bookRepository.save(book);
        savedBook.setTitle("Zmieniony tytuł");

        // when
        Book updatedBook = bookRepository.save(savedBook);

        // then
        assertThat(updatedBook.getTitle()).isEqualTo("Zmieniony tytuł");
    }
    @DisplayName("Test usuwania książki")
    @Test
    void testDeleteBook(){
        // given
        Book savedBook = bookRepository.save(book);

        // when
        bookRepository.delete(savedBook);

        // then
        Optional<Book> deletedBook = bookRepository.findById(savedBook.getId());
        assertThat(deletedBook).isNotPresent();

    }
    @DisplayName("Test wyszukiwania wszystkich książek")
    @Test
    void testFindAllBooks(){
        // given
        bookRepository.save(book);

        // when
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> System.out.println("Książki " + book));

        // then
        assertThat(books).isNotNull();
        assertThat(books.size()).isBetween(0,2);
    }
    @DisplayName("Test wyszukiwania książek po gatunku")
    @Test
    void testFindByGenre(){
        // given
        String genre = bookRepository.save(book).getGenre();

        // when
        List<Book> books = bookRepository.findByGenre(genre);

        // then
        assertThat(books).isNotNull();
    }
    @DisplayName("Test wyszukiwania książek po autorze i roku - pozytywny scenariusz")
    @Test
    void testFindByAuthorAndPublishedYearPositiveScenario(){
        // given
        Book saved = bookRepository.save(book);
        String author = saved.getAuthor();
        int publishedYear = saved.getPublishedYear();

        // when
        List<Book> books = bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);

        // then
        assertThat(books).isNotNull();
        assertThat(books).allMatch(b -> b.getAuthor().equals(author) && b.getPublishedYear() == publishedYear);
    }

    @DisplayName("Test wyszukiwania książek po autorze i roku - negatywny scenariusz")
    @Test
    void testFindByAuthorAndPublishedYearNegativeScenario(){
        // given
        Book saved = bookRepository.save(book);
        String author = "Inny autor";
        int publishedYear = 1000;

        // when
        List<Book> books = bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);

        // then
        assertThat(books).isEmpty();
    }
    //- Test liczenia książek wydanych w danym roku
    //- Test wyszukiwania książek po ISBN
    //- Test wyszukiwania książek tańszych od podanej ceny
    //- Test wyszukiwania dostępnych książek
    //- Test wyszukiwania książek po autorze (Native SQL)
    //- Test wyszukiwania niedawno wydanych książek w danym gatunku
    //- Test zliczania wszystkich książek
    //- Test zliczania wszystkich książek w danym gatunku
    //- Test wyszukiwania najdroższej książki
    //- Test wyszukiwania książek z ocenami powyżej określonego progu
    //- Test aktualizacji dostępności książki
    //- Test dodawania oceny do książki
    //- Test średniej oceny książek danego autora.

}