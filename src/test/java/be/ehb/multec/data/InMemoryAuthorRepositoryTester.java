package be.ehb.multec.data;


import be.ehb.multec.data.util.MySqlConnection;
import be.ehb.multec.model.Author;
import be.ehb.multec.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryAuthorRepositoryTester {

    @Test
    public void getAllAuthors(){
        List<Author> allAuthors = Repositories.getAuthorRepository().getAuthors(null,false);
        for (Author author: allAuthors) {
            System.out.println(author);
        }
    }

    @Test
    public void searchOnName(){
        String filter = "cha";
        List<Author> hits = Repositories.getAuthorRepository().getAuthors(filter,false);
        for(Author a: hits) {
            assertTrue(
                    a.getFirstName().toLowerCase().contains(filter) ||
                            a.getLastName().toLowerCase().contains(filter)
            );
        }
        System.out.println(hits);
    }

    @Test
    public void addAuthor(){
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));

        Repositories.getAuthorRepository().addAuthor(a);
        List<Author> allAuthors = Repositories.getAuthorRepository().getAuthors("",false);
        assertTrue(allAuthors.contains(a));
    }

    @Test
    public void addAuthorTwice(){
        Author authorToInsert = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));

        Repositories.getAuthorRepository().addAuthor(authorToInsert);
        Repositories.getAuthorRepository().addAuthor(authorToInsert);

        List<Author> allAuthors = Repositories.getAuthorRepository().getAuthors("",false);
        int numberOfInsertedAuthors = 0;
        for (Author author: allAuthors) {
            if(author.equals(authorToInsert)) numberOfInsertedAuthors ++;
        }
        assertEquals(1,numberOfInsertedAuthors);
    }

    @Test
    public void setAuthorIdOnInsert(){
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        assertTrue(a.getId()<0);
        Repositories.getAuthorRepository().addAuthor(a);
        assertTrue(a.getId()>0);
    }

    @Test
    public void getAuthorOnId(){
        assertEquals(1,Repositories.getAuthorRepository().getAuthor(1).getId());
    }
}