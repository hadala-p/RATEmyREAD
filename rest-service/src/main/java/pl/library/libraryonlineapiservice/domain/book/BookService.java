package pl.library.libraryonlineapiservice.domain.book;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.library.libraryonlineapiservice.domain.book.dto.BookDto;
import pl.library.libraryonlineapiservice.domain.book.dto.BookSaveApiDto;
import pl.library.libraryonlineapiservice.domain.book.dto.BookSaveDto;
import pl.library.libraryonlineapiservice.domain.genre.Genre;
import pl.library.libraryonlineapiservice.domain.genre.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository,
                       GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
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

    public List<BookDto> findAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(BookDtoMapper::map)
                .toList();
    }
    public List<BookDto> findTopBooks(int size) {
        Pageable page = Pageable.ofSize(size);
        return bookRepository.findTopByRating(page).stream()
                .map(BookDtoMapper::map)
                .toList();
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
        bookRepository.save(book);
    }
    public BookDto createBook(BookSaveApiDto bookToSave) {
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
        book.setImg(bookToSave.getImg());
        Book savedBook = bookRepository.save(book);
        return new BookDto(savedBook.getId(), savedBook.getTitle(), savedBook.getAuthor(), savedBook.getPublisher(),
                savedBook.getRelease_year(), savedBook.getPages(), savedBook.getDescription(), savedBook.getImg(),
                savedBook.getGenre().getName(),
                savedBook.isPromoted(), 0.0, 0);
    }

}
