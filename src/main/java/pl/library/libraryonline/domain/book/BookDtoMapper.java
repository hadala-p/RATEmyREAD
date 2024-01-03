package pl.library.libraryonline.domain.book;

import pl.library.libraryonline.domain.book.dto.BookDto;

public class BookDtoMapper {
    static BookDto map(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getRelease_year(),
                book.getPages(),
                book.getDescription(),
                book.getImg(),
                book.getGenre().getName(),
                book.isPromoted());
    }
}
