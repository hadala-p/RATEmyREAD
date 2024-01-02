package pl.library.libraryonline.Repository;

import org.springframework.data.repository.CrudRepository;
import pl.library.libraryonline.Model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
