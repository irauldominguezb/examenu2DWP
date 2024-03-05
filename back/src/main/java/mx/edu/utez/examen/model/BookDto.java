package mx.edu.utez.examen.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {

    @NotNull
    @Length(min = 3, max = 100)
    private String name;

    @NotNull
    @Length(min = 3, max = 100)
    private String author;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publication;

    public Book parseBook() {
        Book book = new Book();
        book.setName(getName());
        book.setAuthor(getAuthor());
        book.setPublication(getPublication());
        return book;
    }
}
