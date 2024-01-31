package pl.library.libraryonline.domain.book;

import pl.library.libraryonline.domain.book.dto.BookDto;
import pl.library.libraryonline.domain.rating.Rating;

public class BookDtoMapper {
    static BookDto map(Book book) {
        double avgRating = book.getRatings().stream()
                .map(Rating::getRating)
                .mapToDouble(val -> val)
                .average().orElse(0);
        int ratingCount = book.getRatings().size();
        return new BookDto(book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getRelease_year(),
                book.getPages(),
                book.getDescription(),
                book.getImg(),
                book.getGenre().getName(),
                book.isPromoted(),
                avgRating,
                ratingCount);
    }
}
