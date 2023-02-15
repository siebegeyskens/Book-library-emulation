package be.ehb.multec.data;

import be.ehb.multec.model.Author;
import be.ehb.multec.data.util.MySqlConnection;
import be.ehb.multec.model.Book;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlAuthorRepository implements AuthorRepository {

    @Override
    public Author getAuthor(int id) {
        Author author = null;
        final String SQL_SELECT_AUTHOR = "select * from authors where id = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_AUTHOR)) {

            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    author = resultsSet2Author(rs);
            }

        } catch(SQLException e) {
            System.err.println(e);
        }
        return author;
    }

    // query db for author id (used to add a book of an exsisting author bc its id was never set, still -1)
    public int getAuthorId(Author author) {
        int id = -1;
        // TODO: query string
        final String SQL_SELECT_AUTHOR = "SELECT id FROM library.authors where concat(first_name,last_name,birthday) = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_AUTHOR)) {
            stmt.setString(1, author.getFirstName()+author.getLastName()+author.getBirthday());
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    id = rs.getInt("id");
            }

        } catch(SQLException e) {
            System.err.println(e);
        }
        return id;
    }

    public List<Author> getAuthors(String nameFilter, boolean caseSensitive) {
        if(nameFilter == null) nameFilter = "";
        nameFilter = nameFilter.trim();     // wit en new lines er allemaal af
        List<Author> authors = new ArrayList<>();
        String SQL_SELECT_AUTHOR = "select * from authors where concat(first_name,last_name) like concat('%',?,'%')";
        if(caseSensitive)  SQL_SELECT_AUTHOR = "select * from authors where concat(first_name,last_name) like binary concat('%',?,'%')";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_SELECT_AUTHOR)) {

            stmt.setString(1, nameFilter);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next())
                    authors.add(resultsSet2Author(rs));
            }

        } catch(SQLException e) {
            System.err.println(e);
        }
        return authors;
    }

    @Override
    public void removeAuthor(Author author) {
        if (author.getId() < 0)
            author.setId(getAuthorId(author));
        final String SQL_DELETE_AUTHOR = "delete from authors where id = ?";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_DELETE_AUTHOR)) {
            stmt.setInt(1, author.getId());
            int affectedRows = stmt.executeUpdate();

        } catch(SQLException e) {
            System.err.println(e + "\n while trying to remove " + author);
        }
    }

    @Override
    public boolean addAuthor(Author author) {
        final String SQL_INSERT_AUTHOR = "insert into authors(first_name, last_name, birthday) values(?, ?, ?)";
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SQL_INSERT_AUTHOR, PreparedStatement.RETURN_GENERATED_KEYS)) {

            author2PreparedStatement(author, stmt);
            int affectedRows = stmt.executeUpdate();
            try(ResultSet rsKey = stmt.getGeneratedKeys()) {
                if (rsKey.next()) {
                    author.setId(rsKey.getInt(1));
                }
                return true;
            }
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }

    private void author2PreparedStatement(Author author, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, author.getFirstName());
        stmt.setString(2, author.getLastName());
        stmt.setDate(3, Date.valueOf(author.getBirthday()));
    }

    private Author resultsSet2Author(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new Author(id, firstName, lastName, birthday);
    }
}
