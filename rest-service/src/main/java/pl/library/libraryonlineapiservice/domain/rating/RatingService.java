package pl.library.libraryonlineapiservice.domain.rating;

import org.springframework.stereotype.Service;
import pl.library.libraryonlineapiservice.domain.book.Book;
import pl.library.libraryonlineapiservice.domain.user.User;
import pl.library.libraryonlineapiservice.domain.user.UserRepository;
import pl.library.libraryonlineapiservice.domain.book.BookRepository;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addOrUpdateRating(String userEmail, long bookId, int rating) {
        Rating ratingToSaveOrUpdate = ratingRepository.findByUser_EmailAndBook_Id(userEmail, bookId)
                .orElseGet(Rating::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        ratingToSaveOrUpdate.setUser(user);
        ratingToSaveOrUpdate.setBook(book);
        ratingToSaveOrUpdate.setRating(rating);
        ratingRepository.save(ratingToSaveOrUpdate);
    }

    public Optional<Integer> getUserRatingForBook(String userEmail, long bookId) {
        return ratingRepository.findByUser_EmailAndBook_Id(userEmail, bookId)
                .map(Rating::getRating);
    }
}