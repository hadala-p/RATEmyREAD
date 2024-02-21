package pl.library.libraryonlinewebservice.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import pl.library.libraryonlinewebservice.domain.genre.Genre;
import pl.library.libraryonlinewebservice.domain.rating.Rating;
import pl.library.libraryonlinewebservice.domain.user.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void cleanUp() {
        entityManager.getEntityManager().createQuery("DELETE FROM Rating").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Book").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Genre").executeUpdate();
    }


    @Test
    public void whenFindAllByPromotedIsTrue_thenReturnPromotedBooks() {
        // Given
        Genre genre = new Genre();
        genre.setName("Fiction");
        entityManager.persist(genre);

        Book promotedBook = new Book();
        promotedBook.setPromoted(true);
        promotedBook.setGenre(genre);

        entityManager.persist(promotedBook);
        entityManager.flush();

        // When
        List<Book> foundBooks = bookRepository.findAllByPromotedIsTrue();

        // Then
        assertThat(foundBooks).hasSize(1); // Expecting only the promoted book
        assertThat(foundBooks.get(0).isPromoted()).isTrue();
    }

    @Test
    public void whenFindAllByGenreNameIgnoreCase_thenReturnBooksOfThatGenre() {
        // given
        Genre fictionGenre = new Genre();
        fictionGenre.setName("Fiction");
        entityManager.persist(fictionGenre);

        Book bookInGenre = new Book();
        bookInGenre.setGenre(fictionGenre);
        entityManager.persist(bookInGenre);
        entityManager.flush();

        // when
        List<Book> foundBooks = bookRepository.findAllByGenre_NameIgnoreCase("fiction");

        // then
        assertThat(foundBooks).hasSize(1);
        assertThat(foundBooks.get(0).getGenre().getName()).isEqualToIgnoringCase("fiction");
    }

    @Test
    public void whenFindTopByRating_thenReturnTopRatedBooks() {
        // given
        Genre genre = new Genre();
        genre.setName("Fiction");
        entityManager.persist(genre);

        User user = new User();
        entityManager.persist(user);

        Book book1 = new Book();
        book1.setGenre(genre);
        entityManager.persist(book1);

        Rating rating1 = new Rating();
        rating1.setBook(book1);
        rating1.setRating(5);
        rating1.setUser(user);
        entityManager.persist(rating1);

        Book book2 = new Book();
        book2.setGenre(genre);
        entityManager.persist(book2);

        Rating rating2 = new Rating();
        rating2.setBook(book2);
        rating2.setRating(3);
        rating2.setUser(user);
        entityManager.persist(rating2);

        entityManager.flush();

        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Book> foundBooks = bookRepository.findTopByRating(pageRequest);

        // then
        assertThat(foundBooks).hasSize(2);
    }


}
