package be.ehb.multec.data;

import be.ehb.multec.model.Author;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class InMemoryAuthorRepository implements AuthorRepository {

    private final List <Author> authors;

    public InMemoryAuthorRepository() {
        authors = new ArrayList<>();
        authors.add(new Author(1,"George","Orwell", LocalDate.of(1903,06,25)));
        authors.add(new Author(2,"Kris","Pint", LocalDate.of(1981,1,1)));
        authors.add(new Author(3,"Tony","Butt", LocalDate.of(1961,3,1)));
        authors.add(new Author(4,"Charles","Dickens", LocalDate.of(1812,02,07)));
        authors.add(new Author(5,"Jake","Phelps", LocalDate.of(1981, 01, 01)));
    }

    @Override
    public List<Author> getAuthors(String nameFilter, boolean caseSensitive) {
        if(nameFilter == null || nameFilter.trim().length() == 0) return Collections.unmodifiableList(authors);
        List<Author> res = new ArrayList<>();
        for(Author author: authors) {
            String firstName = author.getFirstName();
            String lastName = author.getLastName();
            if(!caseSensitive) {
                firstName = firstName.toLowerCase();
                lastName = lastName.toLowerCase();
                nameFilter = nameFilter.toLowerCase();
            }
            if(firstName.contains(nameFilter) || lastName.contains(nameFilter))
                res.add(author);
        }

        return res;
    }

    @Override
    public Author getAuthor(int id) {
        for(Author author: authors)
            if(author.getId() == id)
                return author;
        return null;
    }

    @Override
    public void removeAuthor(Author author) throws SQLException {
        authors.remove(author);
    }

    @Override
    public int getAuthorId(Author author) {
        return 0;
    }

    private int maxID() {
        int id = -1;
        for(Author author: authors)
            if(author.getId() > id)
                id = author.getId();
        return id;
    }

    @Override
    public boolean addAuthor(Author author) {
        if(getAuthor(author.getId()) == null && !authors.contains(author)) {
            authors.add(author);
            author.setId(maxID()+1);
            return true;
        }
        return false;
    }
}
