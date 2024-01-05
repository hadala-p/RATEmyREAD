package pl.library.libraryonline.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.library.libraryonline.domain.book.BookService;
import pl.library.libraryonline.domain.book.dto.BookSaveDto;
import pl.library.libraryonline.domain.genre.GenreService;
import pl.library.libraryonline.domain.genre.dto.GenreDto;

import java.util.List;

@Controller
public class BookManagementController {
    private final BookService bookService;
    private final GenreService genreService;

    public BookManagementController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping("/admin/dodaj-ksiazke")
    public String addBookForm(Model model) {
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        BookSaveDto book = new BookSaveDto();
        model.addAttribute("book", book);
        return "admin/book-form";
    }

    @PostMapping("/admin/dodaj-ksiazke")
    public String addBook(BookSaveDto book, RedirectAttributes redirectAttributes) {
        bookService.addBook(book);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "książka %s została zapisana".formatted(book.getTitle()));
        return "redirect:/admin";
    }
}
