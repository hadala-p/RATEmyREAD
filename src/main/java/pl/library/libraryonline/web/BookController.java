package pl.library.libraryonline.web;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.library.libraryonline.domain.book.BookService;
import pl.library.libraryonline.domain.book.dto.BookDto;

import java.util.Optional;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable long id, Model model) {
        Optional<BookDto> optionalBook = bookService.findBookById(id);
        optionalBook.ifPresent(book -> model.addAttribute("book", book));
        return "book";
    }
}
