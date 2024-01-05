package pl.library.libraryonline.domain.book;

import org.springframework.stereotype.Service;
import pl.library.libraryonline.domain.book.dto.BookDto;
import pl.library.libraryonline.domain.book.dto.BookSaveDto;
import pl.library.libraryonline.domain.genre.Genre;
import pl.library.libraryonline.domain.genre.GenreRepository;
import pl.library.libraryonline.domain.storage.FileStorageService;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;

    public BookService(BookRepository bookRepository,
                        GenreRepository genreRepository,
                        FileStorageService fileStorageService) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<BookDto> findAllPromotedBooks() {
        return bookRepository.findAllByPromotedIsTrue().stream().map(BookDtoMapper::map).toList();
    }

    public Optional<BookDto> findBookById(long id) {
        return bookRepository.findById(id).map(BookDtoMapper::map);
    }

    public List<BookDto> findBooksByGenreName(String genre) {
        return bookRepository.findAllByGenre_NameIgnoreCase(genre).stream().map(BookDtoMapper::map).toList();
    }

    public void addBook(BookSaveDto bookToSave) {
        Book book = new Book();
        book.setTitle(bookToSave.getTitle());
        book.setAuthor(bookToSave.getAuthor());
        book.setPublisher(bookToSave.getPublisher());
        book.setRelease_year(bookToSave.getRelease_year());
        book.setPages(bookToSave.getPages());
        book.setDescription(bookToSave.getDescription());
        book.setPromoted(bookToSave.isPromoted());
        Genre genre = genreRepository.findByNameIgnoreCase(bookToSave.getGenre()).orElseThrow();
        book.setGenre(genre);
        if (bookToSave.getImg() != null && !bookToSave.getImg().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(bookToSave.getImg());
            book.setImg(savedFileName);
        }
        bookRepository.save(book);
    }

}
