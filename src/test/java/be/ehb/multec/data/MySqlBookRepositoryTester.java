package be.ehb.multec.data;


import be.ehb.multec.data.util.MySqlConnection;
import be.ehb.multec.model.Author;
import be.ehb.multec.model.Book;
import be.ehb.multec.model.Genre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class MySqlBookRepositoryTester {
//    @Test
//    public void getAllBooksByAuthor(){
//        List<Book> books = Repositories.getBookRepository().getBooksbyAuthor("t");
//        System.out.println(books);
//        System.out.println('\n');
//    }

    @Test
    public void addBook(){
        //MySqlConnection.resetDatabase();
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Book b = new Book("Stil de tijd",23.20,"1313131313131",189, Genre.PHILOSOPHY);
        b.setAuthor(a);
        Repositories.getBookRepository().addBook(b);
    }

    @Test
    public void addBookWithExistingAuthor(){
        //MySqlConnection.resetDatabase();
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Book b = new Book("Kairos",19,"1863497891236",260, Genre.PHILOSOPHY);
        b.setAuthor(a);
        Repositories.getBookRepository().addBook(b);
    }

    @Test
    public void getBookId(){
        //MySqlConnection.resetDatabase();
        Book b = new Book("Surf science",25,"1111111111111",205, Genre.SCIENCE);
        System.out.println(Repositories.getBookRepository().getBookId(b));
    }
}