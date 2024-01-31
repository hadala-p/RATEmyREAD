package pl.library.libraryonline.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.library.libraryonline.domain.book.BookService;
import pl.library.libraryonline.domain.book.dto.BookDto;
import pl.library.libraryonline.domain.book.dto.BookSaveApiDto;

import java.net.URI;
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

    @GetMapping("/recommended")
    public List<BookDto> getRecommendedBooks() {
        return bookService.findAllPromotedBooks();
    }

    @PostMapping
    ResponseEntity<BookDto> addBook(@RequestBody BookSaveApiDto bookDto) {
        BookDto addedBook = bookService.createBook(bookDto);
        URI savedJobOfferUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedBook.getId())
                .toUri();
        return ResponseEntity.created(savedJobOfferUri).body(addedBook);
    }
}
