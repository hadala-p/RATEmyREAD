package pl.library.libraryonline.domain.book;

import org.springframework.stereotype.Service;
import pl.library.libraryonline.domain.book.dto.BookDto;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> findAllPromotedBooks() {
        return bookRepository.findAllByPromotedIsTrue().stream().map(BookDtoMapper::map).toList();
    }
}
