package pl.library.libraryonlinewebservice.domain.rating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.library.libraryonlinewebservice.domain.book.Book;
import pl.library.libraryonlinewebservice.domain.book.BookRepository;
import pl.library.libraryonlinewebservice.domain.user.User;
import pl.library.libraryonlinewebservice.domain.user.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddOrUpdateRating_AndRatingDoesNotExist_ThenCreateNewRating() {
        // Given
        String userEmail = "user@example.com";
        long bookId = 1L;
        int ratingValue = 5;

        User user = new User();
        user.setEmail(userEmail);

        Book book = new Book();
        book.setId(bookId);

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(ratingRepository.findByUser_EmailAndBook_Id(userEmail, bookId)).thenReturn(Optional.empty());

        // When
        ratingService.addOrUpdateRating(userEmail, bookId, ratingValue);

        // Then
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void whenAddOrUpdateRating_AndRatingExists_ThenUpdateRating() {
        // Given
        String userEmail = "user@example.com";
        long bookId = 1L;
        int newRatingValue = 4;

        User user = new User();
        Book book = new Book();
        Rating existingRating = new Rating();
        existingRating.setRating(3);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(ratingRepository.findByUser_EmailAndBook_Id(userEmail, bookId)).thenReturn(Optional.of(existingRating));

        // When
        ratingService.addOrUpdateRating(userEmail, bookId, newRatingValue);

        // Then
        verify(ratingRepository).save(existingRating);
        assert (existingRating.getRating() == newRatingValue);
    }

    @Test
    void whenGetUserRatingForBook_ThenReturnRating() {
        // Given
        String userEmail = "user@example.com";
        long bookId = 1L;
        Rating rating = new Rating();
        rating.setRating(5);

        when(ratingRepository.findByUser_EmailAndBook_Id(userEmail, bookId)).thenReturn(Optional.of(rating));

        // When
        Optional<Integer> foundRating = ratingService.getUserRatingForBook(userEmail, bookId);

        // Then
        verify(ratingRepository).findByUser_EmailAndBook_Id(userEmail, bookId);
        assert (foundRating.isPresent() && foundRating.get() == 5);
    }
}
