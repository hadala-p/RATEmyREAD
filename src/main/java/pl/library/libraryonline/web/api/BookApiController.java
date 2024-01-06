package pl.library.libraryonline.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.library.libraryonline.domain.book.BookService;
import pl.library.libraryonline.domain.book.dto.BookDto;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/books",
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE
        })
public class BookApiController {
    private final BookService bookService;

    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<BookDto> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getSingleBook(@PathVariable long id) {
        BookDto book = bookService.findBookById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return book;
    }
}
