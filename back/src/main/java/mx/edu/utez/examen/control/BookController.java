package mx.edu.utez.examen.control;

import jakarta.validation.Valid;
import mx.edu.utez.examen.model.Book;
import mx.edu.utez.examen.model.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = {"*"})
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/")
    Page<Book> getAll (Pageable pageable){
        return service.getAll(pageable);
    }

    @PostMapping("/")
    Book insert(@Valid @RequestBody BookDto book){
        return service.insert(book.parseBook());
    }

    @PutMapping("/{id}")
    Book update(@PathVariable Long id, @Valid @RequestBody BookDto book){
        return service.update(id, book.parseBook());
    }

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable Long id){
        return service.delete(id);
    }
}
