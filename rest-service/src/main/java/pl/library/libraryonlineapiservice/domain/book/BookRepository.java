package pl.library.libraryonlineapiservice.domain.book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAllByPromotedIsTrue();

    List<Book> findAllByGenre_NameIgnoreCase(String genre);
    @Query("select b from Book b join b.ratings r group by b order by avg(r.rating) desc")
    List<Book> findTopByRating(Pageable page);
}
