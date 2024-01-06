package pl.library.libraryonline.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.library.libraryonline.domain.book.BookService;
import pl.library.libraryonline.domain.book.dto.BookDto;
import pl.library.libraryonline.domain.genre.GenreService;
import pl.library.libraryonline.domain.genre.dto.GenreDto;

import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;
    private final BookService bookService;

    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/genre/{name}")
    public String getGenre(@PathVariable String name, Model model) {
        GenreDto genre = genreService.findGenreByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<BookDto> books = bookService.findBooksByGenreName(name);
        model.addAttribute("heading", genre.getName());
        model.addAttribute("description", genre.getDescription());
        model.addAttribute("books", books);
        return "book-listing";
    }

    @GetMapping("/genre")
    public String getGenreList(Model model) {
        List<GenreDto> genres = genreService.findAllGenres();
        model.addAttribute("genres", genres);
        return "genre-listing";
    }
}