package pl.library.libraryonline.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.library.libraryonline.domain.book.BookService;
import pl.library.libraryonline.domain.genre.GenreService;
import pl.library.libraryonline.domain.genre.dto.GenreDto;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreApiController {
    private final GenreService genreService;
    private final BookService bookService;

    public GenreApiController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/{name}")
    public GenreDto getGenre(@PathVariable String name) {
        GenreDto genre = genreService.findGenreByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return genre;
    }

    @GetMapping("")
    public List<GenreDto> getGenreList() {
        return genreService.findAllGenres();
    }
}