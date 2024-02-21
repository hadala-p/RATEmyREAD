package pl.library.libraryonlinewebservice.domain.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import pl.library.libraryonlinewebservice.domain.book.dto.BookDto;
import pl.library.libraryonlinewebservice.domain.genre.Genre;
import pl.library.libraryonlinewebservice.domain.rating.Rating;

import java.util.Set;

public class BookDtoMapperTest {

    @Test
    public void shouldMapBookToBookDto() {
        // Given
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fiction");

        Rating rating1 = new Rating();
        rating1.setId(1L);
        rating1.setRating(5);

        Rating rating2 = new Rating();
        rating2.setId(2L);
        rating2.setRating(4);
        Set<Rating> ratings = Set.of(rating1, rating2);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Example Title");
        book.setAuthor("Example Author");
        book.setPublisher("Example Publisher");
        book.setRelease_year(2021);
        book.setPages(300);
        book.setDescription("Example Description");
        book.setImg("Example Image");
        book.setGenre(genre);
        book.setPromoted(true);
        book.setRatings(ratings);

        // When
        BookDto result = BookDtoMapper.map(book);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Example Title", result.getTitle());
        assertEquals("Example Author", result.getAuthor());
        assertEquals("Example Publisher", result.getPublisher());
        assertEquals(2021, result.getRelease_year());
        assertEquals(300, result.getPages());
        assertEquals("Example Description", result.getDescription());
        assertEquals("Example Image", result.getImg());
        assertEquals("Fiction", result.getGenre());
        assertEquals(true, result.isPromoted());
        assertEquals(4.5, result.getAvgRating()); // Expected average rating
        assertEquals(2, result.getRatingCount()); // Expected count of ratings
    }
}
