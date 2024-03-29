package pl.library.libraryonlinewebservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.library.libraryonlinewebservice.domain.book.BookService;
import pl.library.libraryonlinewebservice.domain.book.dto.BookDto;
import pl.library.libraryonlinewebservice.domain.rating.RatingService;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final RatingService ratingService;

    public BookController(BookService bookService, RatingService ratingService) {
        this.bookService = bookService;
        this.ratingService = ratingService;
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable long id,
                          Model model,
                          Authentication authentication) {
        BookDto book = bookService.findBookById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("book", book);
        //If the user is logged in
        if (authentication != null) {
            String currentUserEmail = authentication.getName();
            //then we look for his vote
            Integer rating = ratingService.getUserRatingForBook(currentUserEmail, id).orElse(0);
            //and save it in the model
            model.addAttribute("userRating", rating);
        }
        return "book";
    }
    @GetMapping("/top10")
    public String findTop10(Model model) {
        List<BookDto> top10Books = bookService.findTopBooks(10);
        model.addAttribute("heading", "TOP 10 książek");
        model.addAttribute("description", "Książki najlepiej oceniane przez użytkowników");
        model.addAttribute("books", top10Books);
        return "book-listing";
    }
}
