package pl.library.libraryonlinewebservice.domain.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.library.libraryonlinewebservice.domain.book.dto.BookDto;
import pl.library.libraryonlinewebservice.domain.book.dto.BookSaveApiDto;
import pl.library.libraryonlinewebservice.domain.book.dto.BookSaveDto;
import pl.library.libraryonlinewebservice.domain.genre.Genre;
import pl.library.libraryonlinewebservice.domain.genre.GenreRepository;
import pl.library.libraryonlinewebservice.domain.storage.FileStorageService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Genre genre;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setName("Fiction");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setGenre(genre);
        book.setAuthor("Author");
        book.setPublisher("Publisher");
        book.setRelease_year(2020);
        book.setPages(100);
        book.setDescription("Description");
        book.setImg("img.jpg");
        book.setPromoted(false);

        bookDto = new BookDto(1L, "Test Book", "Author", "Publisher", 2020, 100, "Description", "img.jpg", "Fiction", false, 0.0, 0);
    }

    @Test
    void whenFindBookByIdWithExistingId_thenBookDtoIsReturned() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When
        Optional<BookDto> foundBookDto = bookService.findBookById(1L);

        // Then
        assertTrue(foundBookDto.isPresent(), "The found book DTO should be present");
        assertEquals(bookDto.getTitle(), foundBookDto.get().getTitle(), "The titles should match");
        assertEquals(bookDto.getGenre(), foundBookDto.get().getGenre(), "The genres should match");
    }


    @Test
    void whenFindBookByIdWithNonExistingId_thenEmptyIsReturned() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<BookDto> foundBookDto = bookService.findBookById(1L);

        // Then
        assertFalse(foundBookDto.isPresent(), "The found book DTO should not be present");
    }

    @Test
    void whenFindBooksByGenreName_thenListOfBookDtosIsReturned() {
        // Given
        String genreName = "Fiction";
        when(bookRepository.findAllByGenre_NameIgnoreCase(genreName)).thenReturn(List.of(book));

        // When
        List<BookDto> foundBooks = bookService.findBooksByGenreName(genreName);

        // Then
        assertEquals(1, foundBooks.size(), "The size of found books should be 1");
        assertEquals(bookDto.getTitle(), foundBooks.get(0).getTitle(), "The titles should match");
        assertEquals(genreName, foundBooks.get(0).getGenre(), "The genres should match");
    }

    @Test
    void whenAddBook_thenBookIsSaved() {
        // Given
        MultipartFile imgFile = new MockMultipartFile("img", "img.jpg", "image/jpeg", "test image content".getBytes());
        BookSaveDto bookSaveDto = new BookSaveDto();
        bookSaveDto.setTitle("Test Book");
        bookSaveDto.setAuthor("Author");
        bookSaveDto.setPublisher("Publisher");
        bookSaveDto.setRelease_year(2020);
        bookSaveDto.setPages(100);
        bookSaveDto.setDescription("Description");
        bookSaveDto.setPromoted(true);
        bookSaveDto.setGenre("Fiction");
        bookSaveDto.setImg(imgFile);
        when(genreRepository.findByNameIgnoreCase("Fiction")).thenReturn(Optional.of(genre));
        when(fileStorageService.saveImage(imgFile)).thenReturn("savedImg.jpg");
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        bookService.addBook(bookSaveDto);

        // Then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book savedBook = bookArgumentCaptor.getValue();
        assertEquals("Test Book", savedBook.getTitle(), "The book title should match");
        assertEquals("Fiction", savedBook.getGenre().getName(), "The genre should match");
        assertEquals("savedImg.jpg", savedBook.getImg(), "The saved image name should match");
    }


    @Test
    void whenCreateBook_thenBookDtoIsReturned() {
        // Given
        BookSaveApiDto bookSaveApiDto = new BookSaveApiDto();
        bookSaveApiDto.setTitle("Test Book");
        bookSaveApiDto.setAuthor("Author");
        bookSaveApiDto.setPublisher("Publisher");
        bookSaveApiDto.setRelease_year(2020);
        bookSaveApiDto.setPages(100);
        bookSaveApiDto.setDescription("Description");
        bookSaveApiDto.setPromoted(true);
        bookSaveApiDto.setGenre("Fiction");
        bookSaveApiDto.setImg("img.jpg");
        when(genreRepository.findByNameIgnoreCase("Fiction")).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookDto createdBookDto = bookService.createBook(bookSaveApiDto);

        // Then
        assertEquals(bookDto.getTitle(), createdBookDto.getTitle(), "The created book title should match");
        assertEquals(bookDto.getGenre(), createdBookDto.getGenre(), "The genre should match");
    }
}