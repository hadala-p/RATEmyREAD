package pl.library.libraryonline.domain.book;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAllByPromotedIsTrue();
    List<Book> findAllByGenre_NameIgnoreCase(String genre);
}
