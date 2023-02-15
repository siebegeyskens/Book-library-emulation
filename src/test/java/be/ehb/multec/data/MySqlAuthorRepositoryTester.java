package be.ehb.multec.data;


import be.ehb.multec.model.Author;
import be.ehb.multec.model.Book;
import be.ehb.multec.model.Genre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class MySqlAuthorRepositoryTester {
    @Test
    public void getAllAuthors(){
        List<Author> authors = Repositories.getAuthorRepository().getAuthors("",false);
        for(Author a: authors) {
            System.out.println(a);
        }
        System.out.println('\n');
    }
    @Test
    public void getAllAuthorsWithFilter(){
        List<Author> authors = Repositories.getAuthorRepository().getAuthors("g",false);
        for(Author a: authors) {
            System.out.println(a);
        }
        System.out.println('\n');
    }
    @Test
    public void addExistingAuthor(){
        System.out.println("---           added author twice          ---");
        //MySqlConnection.resetDatabase();
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Repositories.getAuthorRepository().addAuthor(a);
        Repositories.getAuthorRepository().addAuthor(a);
        System.out.println('\n');
    }
    @Test
    public void getId(){
        //MySqlConnection.resetDatabase();
        System.out.println("---          get id         ---");
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        System.out.println(Repositories.getAuthorRepository().getAuthorId(a));
        System.out.println('\n' );
    }
}