package be.ehb.multec.data;

import be.ehb.multec.model.Book;

import java.util.List;

    //  1 repository per entiteit
public interface BookRepository {
    List<Book> getBooks(String nameFilter, boolean caseSensitive);
    // List<Book> getBooksbyAuthor(String authorNameFilter);
    Book getBook(int id);
    void removeBook(Book book);
    void updatePrice(Book book, double newSellingPrice);
    int getBookId(Book book);
        //    //void changeBook(Book student);
    void addBook(Book book);
}
