package pl.library.libraryonlinewebservice.domain.rating;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Optional<Rating> findByUser_EmailAndBook_Id(String userEmail, Long bookId);
}