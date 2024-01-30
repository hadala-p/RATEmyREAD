package pl.library.libraryonline.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(
        path = "/api/genres",
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE
        })
public class GenreApiController {
    private final GenreService genreService;

    public GenreApiController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{name}")
    public GenreDto getGenre(@PathVariable String name) {
        GenreDto genre = genreService.findGenreByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return genre;
    }

    @GetMapping("/all")
    public List<GenreDto> getGenreList() {
        return genreService.findAllGenres();
    }
}