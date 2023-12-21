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
    void testSavedBook() { // przemyśleć czemu nie przechodzi jako część wszystkich testów
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
    void findById() {
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
    void testUpdateBook() {
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
    void testDeleteBook() {
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
    void testFindAllBooks() {
        // given
        bookRepository.save(book);

        // when
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> System.out.println("Książki " + book));

        // then
        assertThat(books).isNotNull();
        assertThat(books.size()).isBetween(0, 2);
    }

    @DisplayName("Test wyszukiwania książek po gatunku")
    @Test
    void testFindByGenre() {
        // given
        String genre = bookRepository.save(book).getGenre();

        // when
        List<Book> books = bookRepository.findByGenre(genre);

        // then
        assertThat(books).isNotNull();
    }

    @DisplayName("Test wyszukiwania książek po autorze i roku - pozytywny scenariusz")
    @Test
    void testFindByAuthorAndPublishedYearPositiveScenario() {
        // given
        Book saved = bookRepository.save(book);
        String author = saved.getAuthor();
        int publishedYear = saved.getPublishedYear();

        // when
        List<Book> books = bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getAuthor().equals(author) && b.getPublishedYear() == publishedYear);
    }

    @DisplayName("Test wyszukiwania książek po autorze i roku - negatywny scenariusz")
    @Test
    void testFindByAuthorAndPublishedYearNegativeScenario() {
        // given
        bookRepository.save(book);
        String author = "Inny autor";
        int publishedYear = 1000;

        // when
        List<Book> books = bookRepository.findBooksByAuthorAndPublishedYear(author, publishedYear);

        // then
        assertThat(books).isEmpty();
    }

    @DisplayName("Test liczenia książek wydanych w danym roku")
    @Test
    void testCountBooksByPublishedYear() {
        // given
//        Book newBook = new Book(
//                2L,
//                "Hobbit",
//                "J.R.R Tolkien",
//                "1234567890000",
//                1939,
//                "Fantasy",
//                new BigDecimal("49.99"),
//                true,
//                Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
//        bookRepository.save(newBook);
        int year = 1954;

        // when
        int count = bookRepository.countBooksByPublishedYear(year);

//        System.out.println(count);
        // then
        assertThat(count).isGreaterThan(0);
    }

    @DisplayName("Test wyszukiwania książki po ISBN")
    @Test
    void testFindBookByISBN() {
        // given
        bookRepository.save(book);
        String isbn = "1234567890123";

        // when
        Book findedBook = bookRepository.findBookByIsbn(isbn).get();

        // then
        assertThat(findedBook).isNotNull();
        assertThat(findedBook.getIsbn()).isEqualTo(isbn);
    }

    @DisplayName("Test wyszukiwania książek tańszych od podanej ceny v1")
    @Test
    void testFindBooksCheaperThan() {
        // given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");

        // when
        List<Book> books = bookRepository.findBooksCheaperThan(price);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getPrice().compareTo(price) < 0);
    }

    @Test
    @DisplayName("Test wyszukiwania książek tańszych od podanej ceny v2")
    void testFindByPriceLessThan() {
        // given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");

        // when
        List<Book> books = bookRepository.findByPriceLessThan(price);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getPrice().compareTo(price) < 0);
    }

    @Test
    @DisplayName("Test wyszukiwania książek tańszych od podanej ceny v3")
    void testFindAllByPriceBefore() {
        // given
        bookRepository.save(book);
        BigDecimal price = new BigDecimal("50.00");

        // when
        List<Book> books = bookRepository.findAllByPriceBefore(price);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getPrice().compareTo(price) < 0);
    }


    @DisplayName("Test wyszukiwania dostępnych książek v2")
    @Test
    void testFindBooksByAvailableTrue() {
        // given
        Book newBook = new Book(
                2L,
                "Hobbit",
                "J.R.R Tolkien",
                "1234567890000",
                1939,
                "Fantasy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
        bookRepository.save(newBook);

        // when
        List<Book> books = bookRepository.findAllAvailableBooks();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(Book::getAvailable);
    }

    @DisplayName("Test wyszukiwania dostępnych książek")
    @Test
    void testFindAllAvailableBooks() {
        // given
        Book newBook = new Book(
                2L,
                "Hobbit",
                "J.R.R Tolkien",
                "1234567890000",
                1939,
                "Fantasy",
                new BigDecimal("49.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
        bookRepository.save(newBook);

        // when
        List<Book> books = bookRepository.findBooksByAvailableTrue();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(Book::getAvailable);
    }

    @DisplayName("Test wyszukiwania książek po autorze")
    @Test
    void testFindByAuthorSQL() {
        // given
        Book newBook = new Book(
                2L,
                "Harry Potter",
                "J.K. Rowling",
                "1234567890010",
                2001,
                "Fantasy",
                new BigDecimal("29.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
        bookRepository.save(newBook);
        String author = newBook.getAuthor();

        // when
        List<Book> books = bookRepository.findByAuthor(author);


        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getAuthor().equals(author)); // Predykat zwraca zawsze 'True' dla pustego zbioru 'prawo pustego zbioru'
    }

    @DisplayName("Test wyszukiwania niedawno wydanych książek w danym gatunku")
    @Test
    void testFindRecentBooksByGenre() {
// given
        Book newBook = new Book(
                2L,
                "Harry Potter",
                "J.K. Rowling",
                "1234567890010",
                2001,
                "Fantasy",
                new BigDecimal("29.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
        bookRepository.save(newBook);
        int year = 1000;
        String genre = "Fantasy";

        // when
        List<Book> books = bookRepository.findRecentBooksByGenre(year, genre);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getPublishedYear() > year && b.getGenre().equals(genre));
        assertThat(books.size()).isEqualTo(2);
    }

    @DisplayName("Test wyszukiwania niedawno wydanych książek w danym gatunku")
    @Test
    void testFindAllByPublishedYearAfterAndGenre() {
        // given
        Book newBook = new Book(
                2L,
                "Harry Potter",
                "J.K. Rowling",
                "1234567890010",
                2001,
                "Fantasy",
                new BigDecimal("29.99"),
                true,
                Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
        bookRepository.save(newBook);
        int year = 1000;
        String genre = "Fantasy";

        // when
        List<Book> books = bookRepository.findAllByPublishedYearAfterAndGenre(year, genre);

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getPublishedYear() > year && b.getGenre().equals(genre));
        assertThat(books.size()).isEqualTo(2);
    }

    @DisplayName("Test zliczania wszystkich książek")
    @Test
    void testCountAllBooks() {
        // given
        bookRepository.save(book);

        // when
        long count = bookRepository.count();

        // then
        assertThat(count).isGreaterThan(0);
    }

    @DisplayName("Test zliczania wszystkich książek w danym gatunku")
    @Test
    void testCountingBooksByGenre() {
        // given
        Book saved = bookRepository.save(book);

        // when
        long count = bookRepository.countByGenre(saved.getGenre());

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("Test wyszukiwania najdroższej książki")
    @Test
    void testFindMostExpensiveBooks() {
        // given
        bookRepository.save(book);

        // when
        List<Book> books = bookRepository.findMostExpensiveBooks();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
    }

    @DisplayName("Test wyszukiwania najdroższej książki z 5-ciu")
    @Test
    void testFindMostExpensiveBooksFromFive() {
        Book newBook1 = new Book(2L, "Harry Potter1", "J.K. Rowling", "2234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(5, 4, 5, 4, 5));

        Book newBook2 = new Book(3L, "Harry Potter2", "J.K. Rowling", "3234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(5, 4, 5, 4, 5));

        Book newBook3 = new Book(4L, "Harry Potter3", "J.K. Rowling", "4234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(5, 4, 5, 4, 5));

        Book newBook4 = new Book(5L, "Harry Potter4", "J.K. Rowling", "5234567890010", 2001, "Fantasy",
                new BigDecimal("29.99"), true, Arrays.asList(5, 4, 5, 4, 5));
        bookRepository.save(book);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        bookRepository.save(newBook4);

        // when
        List<Book> books = bookRepository.findMostExpensiveBooksNative();

        // then
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(3);
        assertThat(books).allMatch(b -> b.getPrice().equals(new BigDecimal("99.99")));
    }

    @DisplayName("Test wyszukiwania książek z ocenami powyżej określonego progu")
    @Test
    void testFindBooksWithRatingsAboveThreshold() {
        // given
        Book newBook1 = new Book(2L, "Harry Potter1", "J.K. Rowling", "2234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(1, 2, 3, 4, 5));

        Book newBook2 = new Book(3L, "Harry Potter2", "J.K. Rowling", "3234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(6, 7, 8, 9, 10));

        Book newBook3 = new Book(4L, "Harry Potter3", "J.K. Rowling", "4234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(0, 1, 1, 1, 0));

        Book newBook4 = new Book(5L, "Harry Potter4", "J.K. Rowling", "5234567890010", 2001, "Fantasy",
                new BigDecimal("29.99"), true, Arrays.asList(2, 1, 2, 1, 5));
        bookRepository.save(book);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        bookRepository.save(newBook4);
        int ratingThreshold = 4;

        // when
        List<Book> books = bookRepository.findBooksWithRatingsAbove(ratingThreshold);
        books.forEach(book -> System.out.printf("Książka z ratingiem %s: oraz id: %d%n", book.getRatings(), book.getId()));
        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getRatings().stream().anyMatch(rating -> rating > ratingThreshold));
    }

    @DisplayName("Test wyszukiwania książek z ocenami powyżej określonego progu")
    @Test
    void testFindBooksWithMinimumRatingsAboveThreshold() {
        // given
        Book newBook1 = new Book(2L, "Harry Potter1", "J.K. Rowling", "2234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(1, 2, 3, 4, 5));

        Book newBook2 = new Book(3L, "Harry Potter2", "J.K. Rowling", "3234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(6, 7, 8, 9, 10));

        Book newBook3 = new Book(4L, "Harry Potter3", "J.K. Rowling", "4234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(4, 4, 12, 4, 5));

        Book newBook4 = new Book(5L, "Harry Potter4", "J.K. Rowling", "5234567890010", 2001, "Fantasy",
                new BigDecimal("29.99"), true, Arrays.asList(2, 1, 2, 1, 5));
        bookRepository.save(book);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        bookRepository.save(newBook4);
        int ratingThreshold = 4;

        // when
        List<Book> books = bookRepository.findBooksWithMinimumRatingsAbove(ratingThreshold);
        books.forEach(book -> System.out.printf("Książka z ratingiem %s: oraz id: %d%n", book.getRatings(), book.getId()));
        // then
        assertThat(books).isNotEmpty();
        assertThat(books).allMatch(b -> b.getRatings().stream().anyMatch(rating -> rating > ratingThreshold));
    }

    @DisplayName("Test średniej oceny książek danego autora")
    @Test
    void testCalculateAverageRatingByAuthor() {
        // given
        Book newBook1 = new Book(2L, "Harry Potter1", "J.K. Rowling", "2234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(1, 2, 3, 4, 5));

        Book newBook2 = new Book(3L, "Harry Potter2", "J.K. Rowling", "3234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(6, 7, 8, 9, 10));

        Book newBook3 = new Book(4L, "Harry Potter3", "J.K. Rowling", "4234567890010", 2001, "Fantasy",
                new BigDecimal("99.99"), true, Arrays.asList(4, 4, 12, 4, 5));

        Book newBook4 = new Book(5L, "Harry Potter4", "J.K. Rowling", "5234567890010", 2001, "Fantasy",
                new BigDecimal("29.99"), true, Arrays.asList(2, 1, 2, 1, 5));
        bookRepository.save(book);
        bookRepository.save(newBook1);
        bookRepository.save(newBook2);
        bookRepository.save(newBook3);
        bookRepository.save(newBook4);

        List<Book> bookList = bookRepository.findByAuthor(book.getAuthor());

        // when
        Double avgRating = bookRepository.calculateAverageRatingByAuthor(book.getAuthor());
        // then
        assertThat(avgRating).isNotNull();
        assertThat(avgRating).isGreaterThanOrEqualTo(countAvgRating(bookList)); // metoda z klasy testowej
        assertThat(avgRating).isGreaterThanOrEqualTo(bookList.stream().mapToDouble(Book::countAvgRating).average().orElse(0.0)); // metoda z klasy book
    }

    private static Double countAvgRating(List<Book> bookList) {
        double result = 0.0d;
        for (Book book1 : bookList) {
            result += book1.getRatings().stream().mapToInt(Integer::intValue).average().orElse(0.0);
        }
        return result;
    }

    @DisplayName("Test aktualizacji dostępności książki")
    @Test
    void testUpdatedBookAvailability(){
        // given
        Book savedBook = bookRepository.save(book);
        boolean newAvailabilityStatus = false;

        // when
        savedBook.setAvailable(newAvailabilityStatus);
        Book updatedBook = bookRepository.save(savedBook);

        // then
        assertThat(updatedBook.getAvailable()).isEqualTo(newAvailabilityStatus);
    }


    @DisplayName("Test dodawania oceny do książki")
    @Test
    void testAddRatingToBook (){
        // given
        Book savedBook = bookRepository.save(book);
        int newRating = 9;

        // when
        savedBook.getRatings().add(newRating);
        Book updatedBook = bookRepository.save(savedBook);

        // then
        assertThat(updatedBook.getRatings()).contains(newRating);
        assertThat(updatedBook.getRatings().size()).isEqualTo(6);
    }

}