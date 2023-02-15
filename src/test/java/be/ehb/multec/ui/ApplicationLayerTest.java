package be.ehb.multec.ui;

import be.ehb.multec.data.Repositories;
import be.ehb.multec.data.util.MySqlConnection;
import be.ehb.multec.model.Author;
import be.ehb.multec.model.Book;
import be.ehb.multec.model.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationLayerTest {

    String filter;
    private List<Book> searchBooks(String filter) {
        return Repositories.getBookRepository().getBooks(filter, false);
    }

    @BeforeEach
    public void resetDb(){
        MySqlConnection.resetDatabase();
    }

    @Test
    public void searchAllBooks(){
        filter = "";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                            b.getTitle().toLowerCase().contains(filter) ||
                            b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                            b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        for(Book b: searchBooks(filter)) {
//            System.out.println(b);
//        }
    }

    @Test
    public void searchBooksWithRubbish(){
        filter = "sdfqsdfdfq";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                            b.getTitle().toLowerCase().contains(filter) ||
                            b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                            b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        for(Book b: searchBooks(filter)) {
//            System.out.println(b);
//        }
    }

    @Test
    public void searchBooksOnTitle(){
        filter = "animal farm";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                    b.getTitle().toLowerCase().contains(filter) ||
                    b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                    b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        System.out.println("---           searched for: animal farm          ---");
//        for(Book b: searchBooks(filter)){
//            System.out.println(b);
//        }
    }

    @Test
    public void searchBooksOnPartOfTitle(){
        filter = "de";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                            b.getTitle().toLowerCase().contains(filter) ||
                            b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                            b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        System.out.println("---           searched for: de           ---");
//        for(Book b: searchBooks(filter)) {
//            System.out.println(b);
//        }
    }

    @Test
    public void searchBooksOnISBN(){
        filter = "3456789123456";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                            b.getTitle().toLowerCase().contains(filter) ||
                            b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                            b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        System.out.println("---           searched for: 3456789123456           ---");
//        for(Book b: searchBooks(filter)) {
//            System.out.println(b);
//        }
    }

    @Test
    public void searchBooksOnPartOfISBN(){
        filter = "9";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                            b.getTitle().toLowerCase().contains(filter) ||
                            b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                            b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        System.out.println("---           searched for :9           ---");
//        for(Book b: searchBooks(filter)) {
//            System.out.println(b);
//        }
    }

    @Test
    public void searchBooksOnAuthor(){
        filter = "jake";
        for(Book b: searchBooks(filter)) {
            assertTrue(
                    b.getIsbn13().toLowerCase().contains(filter) ||
                            b.getTitle().toLowerCase().contains(filter) ||
                            b.getAuthor().getFirstName().toLowerCase().contains(filter) ||
                            b.getAuthor().getLastName().toLowerCase().contains(filter)
            );
        }
//        System.out.println("---           searched for: jake (on author)          ---");
//        for(Book b: searchBooks(filter)) {
//            System.out.println(b);
//        }
    }


    @Test
    public void addBook(){
        Author insertedAuthor = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Book insertedBook = new Book("Stil de tijd",23.20,"1313131313131",189, Genre.PHILOSOPHY);
        insertedBook.setAuthor(insertedAuthor);

        Repositories.getBookRepository().addBook(insertedBook);

        List<Book> booksTable = searchBooks(null);
        assertTrue(booksTable.contains(insertedBook));
    }

    @Test
    public void addBookWithoutAuthor(){
        Book b = new Book("Surf is where you find it",35,"0000000000000",250, Genre.SCIENCE);
        try{
            Repositories.getBookRepository().addBook(b);
            fail();
        }catch(NullPointerException e){
            assertEquals("author was never set",e.getMessage());
        }
    }

    @Test
    public void addMultipleBooksOfSameAuthorInOneTime() {
        Author insertedAuthor  = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Book b = new Book("Kairos",19,"1863497891236",260, Genre.PHILOSOPHY);
        Book b2 = new Book("Stil de tijd",23.20,"1313131313131",189, Genre.PHILOSOPHY);
        b2.setAuthor(insertedAuthor);
        b.setAuthor(insertedAuthor);

        Repositories.getBookRepository().addBook(b);
        Repositories.getBookRepository().addBook(b2);

        List<Book> booksTable = searchBooks(null);
        assertTrue(booksTable.contains(b) && booksTable.contains(b2));

        List<Author> authorsTable = Repositories.getAuthorRepository().getAuthors(null,false);
        int numberOfInsertedAuthors = 0;
        for (Author selectedAuthor: authorsTable) {
            if(selectedAuthor.equals(insertedAuthor)) numberOfInsertedAuthors ++;
        }
        assertFalse(numberOfInsertedAuthors > 1);
    }

    @Test
    public void addBookOfExistingAuthorOnDifferentTimes(){

        Book b = new Book("Kairos",19,"1863497891236",260, Genre.PHILOSOPHY);
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        b.setAuthor(a);
        Author a2 = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Book b2 = new Book("Stil de tijd",23.20,"1313131313131",189, Genre.PHILOSOPHY);
        b2.setAuthor(a2);

        Repositories.getBookRepository().addBook(b2);
        Repositories.getBookRepository().addBook(b);

        List<Book> booksTable = searchBooks(null);
        assertTrue(booksTable.contains(b) || booksTable.contains(b2));
    }

    @Test
    public void addExistingBook(){
        Author a = new Author("Joke", "Hermsen", LocalDate.of(1970,2,2));
        Book insertedBook = new Book("Kairos",19,"1863497891236",260, Genre.PHILOSOPHY);
        insertedBook.setAuthor(a);

        Repositories.getBookRepository().addBook(insertedBook);
        Repositories.getBookRepository().addBook(insertedBook);

        List<Book> booksTable = searchBooks(null);
        int numberOfInsertedBooks = 0;
        for (Book selectedBook: booksTable) {
            if(selectedBook.equals(insertedBook)) numberOfInsertedBooks ++;
        }
        assertEquals(1, numberOfInsertedBooks);
    }

    @Test
    public void deleteAuthorForWhichABookExists() throws SQLException {
        Author a = new Author("Tony", "Butt", LocalDate.of(1961,03,01));

        Repositories.getAuthorRepository().removeAuthor(a);

        List<Author> authorTable = Repositories.getAuthorRepository().getAuthors(null,false);
        assertTrue(authorTable.contains(a));
    }

    @Test
    public void deleteAuthorForWhichNoBookExists() throws SQLException {
        Author a = new Author("Charles", "Dickens", LocalDate.of(1812,2,7));
        List<Author> authorTable = Repositories.getAuthorRepository().getAuthors(null,false);
        assertTrue(authorTable.contains(a));

        Repositories.getAuthorRepository().removeAuthor(a);
        authorTable = Repositories.getAuthorRepository().getAuthors(null,false);
        assertFalse(authorTable.contains(a));
    }

    @Test
    public void deleteBook() throws SQLException {
        Book b = new Book("Surf science",25,"1111111111111",205, Genre.SCIENCE);
        List<Book> booksTable = Repositories.getBookRepository().getBooks(null, false);
        assertTrue(booksTable.contains(b));

        Repositories.getBookRepository().removeBook(b);
        booksTable = Repositories.getBookRepository().getBooks(null,false);
        assertFalse(booksTable.contains(b));
    }

    @Test
    public void updatePrice(){
        Book b = Repositories.getBookRepository().getBook(1);
        double currentPrice = b.getSellingPrice();
        double priceToSet = 27.50;
        assertTrue(currentPrice != priceToSet);

        Repositories.getBookRepository().updatePrice(b,priceToSet);
        double updatedPrice = Repositories.getBookRepository().getBook(b.getId()).getSellingPrice();
        assertEquals(priceToSet,updatedPrice);
    }
}