package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;

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
    void testSavedBook(){
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
    //- Test wyszukiwania po Id
    //- Test aktualizacji książki
    //- Test usuwania książki
    //- Test wyszukiwania wszystkich książek
    //- Test wyszukiwania książek po gatunku
    //- Test wyszukiwania książek po autorze i roku
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