package pl.library.libraryonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.library.libraryonline.Model.Book;
import pl.library.libraryonline.Repository.BookRepository;

@SpringBootApplication
public class LibraryOnlineApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LibraryOnlineApplication.class, args);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        Book book1 = new Book("Kryminał", "W pustyni i w puszczy", "Sienkiewicz", "Nowa era", 2000,
                654, "malowniczy świat", "img");
        bookRepository.save(book1);
        Book book2 = new Book("Dramat", "Potop", "Sienkiewicz", "Helium", 1999,
                765, "Super dramat", "img");
        bookRepository.save(book2);

    }

}
