package pl.library.libraryonlinewebservice.domain.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.library.libraryonlinewebservice.domain.genre.Genre;
import pl.library.libraryonlinewebservice.domain.genre.GenreRepository;
import pl.library.libraryonlinewebservice.domain.genre.GenreService;
import pl.library.libraryonlinewebservice.domain.genre.dto.GenreDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindGenreByName_thenGenreShouldBeReturned() {
        // Given
        String genreName = "Fiction";
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName(genreName);
        genre.setDescription("A genre of imaginative fiction");
        when(genreRepository.findByNameIgnoreCase(genreName)).thenReturn(Optional.of(genre));

        // When
        Optional<GenreDto> found = genreService.findGenreByName(genreName);

        // Then
        assertThat(found).isPresent();
        found.ifPresent(genreDto -> {
            assertThat(genreDto.getName()).isEqualTo(genreName);
            assertThat(genreDto.getDescription()).isEqualTo(genre.getDescription());
        });
    }

    @Test
    void whenFindAllGenres_thenAllGenresShouldBeReturned() {
        // Given
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");
        genre1.setDescription("Fiction description");
        Genre genre2 = new Genre();
        genre2.setId(2L);
        genre2.setName("Non-Fiction");
        genre2.setDescription("Non-Fiction description");
        when(genreRepository.findAll()).thenReturn(List.of(genre1, genre2));

        // When
        List<GenreDto> foundGenres = genreService.findAllGenres();

        // Then
        assertThat(foundGenres).hasSize(2);
        assertThat(foundGenres.get(0).getName()).isEqualTo(genre1.getName());
        assertThat(foundGenres.get(1).getName()).isEqualTo(genre2.getName());
    }

    @Test
    void whenAddGenre_thenGenreShouldBeSaved() {
        // Given
        GenreDto newGenreDto = new GenreDto(null, "History", "A genre about historical events");

        Genre genreToSave = new Genre();
        genreToSave.setId(null);
        genreToSave.setName(newGenreDto.getName());
        genreToSave.setDescription(newGenreDto.getDescription());

        Genre savedGenre = new Genre();
        savedGenre.setId(1L);
        savedGenre.setName(newGenreDto.getName());
        savedGenre.setDescription(newGenreDto.getDescription());

        when(genreRepository.save(any(Genre.class))).thenReturn(savedGenre);

        // When
        GenreDto savedGenreDto = genreService.addGenre(newGenreDto);

        // Then
        assertThat(savedGenreDto.getId()).isEqualTo(savedGenre.getId());
        assertThat(savedGenreDto.getName()).isEqualTo(newGenreDto.getName());
        assertThat(savedGenreDto.getDescription()).isEqualTo(newGenreDto.getDescription());

        // Verify interactions
        verify(genreRepository, times(1)).save(any(Genre.class));
    }
}
