package mx.edu.utez.examen.control;

import jakarta.transaction.Transactional;
import mx.edu.utez.examen.model.Book;
import mx.edu.utez.examen.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Page<Book> getAll (Pageable pageable){
        return repository.findAll(pageable);
    }

    @Transactional
    public Book insert(Book book){
        return repository.save(book);
    }

    @Transactional
    public Book update(Long id, Book book){
        if (!repository.existsById(id)) return new Book();
        book.setId(id);
        return repository.saveAndFlush(book);
    }

    @Transactional
    public boolean delete(Long id){
        try {
            if (!repository.existsById(id)) return false;
            repository.deleteById(id);
            return true;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
