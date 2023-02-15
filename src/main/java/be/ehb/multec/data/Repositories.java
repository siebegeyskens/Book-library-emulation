package be.ehb.multec.data;

public class Repositories {
    private static final BookRepository BOOK_REPO = new MySqlBookRepository();         // voor de business en ui layer maakt het niet uit dewelke
    private static final AuthorRepository AUTHOR_REPO = new MySqlAuthorRepository();
   // private static final AuthorRepository AUTHOR_REPO = new InMemoryAuthorRepository();   // twee verschillende implementaties van de interface

    private Repositories() {}

    public static BookRepository getBookRepository() {
            return BOOK_REPO;
        }
    public static AuthorRepository getAuthorRepository() {
        return AUTHOR_REPO;
    }
}
