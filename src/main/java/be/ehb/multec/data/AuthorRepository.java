package be.ehb.multec.data;

import be.ehb.multec.model.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorRepository {
    List<Author> getAuthors(String nameFilter, boolean caseSensitive);

    Author getAuthor(int id);

    void removeAuthor(Author author) throws SQLException;
//
//    //void changeAuthor(Author address);
    int getAuthorId(Author author);
    boolean addAuthor(Author author);
}
