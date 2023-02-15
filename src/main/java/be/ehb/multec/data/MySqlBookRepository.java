package be.ehb.multec.data;

import be.ehb.multec.model.Author;
import be.ehb.multec.model.Genre;
import be.ehb.multec.model.Book;
import be.ehb.multec.data.util.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlBookRepository implements BookRepository {

    @Override
    public List<Book> getBooks(String filter, boolean caseSensitive) {
        if(filter == null) filter = "";
        filter = filter.trim();     // wit en new lines er allemaal af
        List<Book> books = new ArrayList<>();
        String SQL_SELECT_BOOKS = "select * from books left join authors on books.author = authors.id  where concat(title,ISBN13,first_name,last_name) like concat('%',?,'%')";
        if(caseSensitive) SQL_SELECT_BOOKS = "select * from books left join authors on books.author = authors.id  where concat(title,ISBN13,first_name,last_name) like binary concat('%',?,'%')";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_BOOKS)) {

            stmt.setString(1, filter);

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next())           // verschuif pointer in de resultset (ga naar volgende rij)
                    books.add(resultsSet2Book(rs));
            }

        } catch(SQLException e) {
            System.err.println(e);
        }
        return books;
    }

//    @Override
//    public List<Book> getBooksbyAuthor(String authorNameFilter) {
//        List<Book> books = new ArrayList<>();
//        List<Author> authors = Repositories.getAuthorRepository().getAuthors(authorNameFilter,false);       // get a list of all authors for the filter
//        final String SQL_SELECT_BOOKS= "select * from books where author = ?";
//        try(Connection con = MySqlConnection.getConnection();
//            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_BOOKS)) {
//            for(Author a:authors){
//                stmt.setInt(1, a.getId());
//                try(ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next())           // verschuif pointer in de resultset (ga naar volgende rij)
//                        books.add(resultsSet2Book(rs));
//                }
//            }
//
//        } catch(SQLException e) {
//            System.err.println(e);
//        }
//        return books;
//    }

    @Override
    public Book getBook(int id) {
        Book book = null;
        final String SQL_SELECT_STUDENT = "select * from books where id = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_STUDENT)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    book = resultsSet2Book(rs);
            }

        } catch(SQLException e) {
            System.err.println(e);
        }
        return book;
    }




    @Override
    public void removeBook(Book book) {
        final String SQL_DELETE_STUDENT = "delete from books where ISBN13 = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_DELETE_STUDENT)) {

            stmt.setString(1, book.getIsbn13());
            int affectedRows = stmt.executeUpdate();

        } catch(SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void addBook(Book book) {
        final String SQL_INSERT_BOOK = "insert into books(title, selling_price, ISBN13, pages, genre, author) values(?, ?, ?, ?, ?, ?)";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_INSERT_BOOK, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if(book.getAuthor() != null) {
                if (!Repositories.getAuthorRepository().addAuthor(book.getAuthor())) {
                    book.getAuthor().setId(Repositories.getAuthorRepository().getAuthorId(book.getAuthor()));  // set id of existing author
                }
            }else{
                throw new NullPointerException("author was never set");
            }

            book2PreparedStatement(book, stmt);

            int affectedRows = stmt.executeUpdate();
            try(ResultSet rsKey = stmt.getGeneratedKeys()) {
                if (rsKey.next()) book.setId(rsKey.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public int getBookId(Book book) {
        int id = -1;
        final String SQL_SELECT_BOOK = "SELECT id FROM library.books where ISBN13 = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_BOOK)) {
            stmt.setString(1, book.getIsbn13());
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    id = rs.getInt("id");
            }

        } catch(SQLException e) {
            System.err.println(e);
        }
        return id;
    }

    public void updatePrice(Book book, double newSellingPrice){
        book.setId(getBookId(book));
        final String SQL_UPDATE_BOOK = "update books set selling_price = ?  where id = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_BOOK)) {
            stmt.setInt(2, book.getId());
            stmt.setDouble(1, newSellingPrice);
            stmt.executeUpdate();

        } catch(SQLException e) {
            System.err.println(e);
        }
    }

    private void book2PreparedStatement(Book book, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, book.getTitle());
        stmt.setDouble(2, book.getSellingPrice());
        stmt.setString(3, book.getIsbn13());   // Data.value.of(EEN JAVA LOCALDATE) omzetten van type naar slq date type
        stmt.setInt(4, book.getPages());
        stmt.setString(5, book.getGenre().toString());
        stmt.setInt(6, book.getAuthor().getId());
    }

    private Book resultsSet2Book(ResultSet rs) throws SQLException {  // vang tab.data (1rij) van query result op
                                                                            // en maak er een book van
        int id = rs.getInt("id");
        String title = rs.getString("title");
        double sellingPrice = rs.getDouble("selling_price");
        Genre genre = Genre.valueOf(rs.getString("genre"));
        String isbn13 = rs.getString("ISBN13");
        int pages = rs.getInt("pages");
        Book book = new Book(id,title,sellingPrice,isbn13,pages,genre);
        Author author = Repositories.getAuthorRepository().getAuthor(rs.getInt("author"));        //query again for referenced author(id)
        book.setAuthor(author);
        return book;
    }
}
