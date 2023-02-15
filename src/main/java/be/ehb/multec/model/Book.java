package be.ehb.multec.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Book {
    private int id;
    private final String title;
    private final double sellingPrice;
    private final String isbn13; // Local
    private final int pages;
    private final Genre genre;
    private Author author;

    public Book(String title, double sellingPrice, String isbn13,int pages ,Genre genre) {
        this(-1, title, sellingPrice, isbn13,pages ,genre);
    }

    public Book(int id, String title, double sellingPrice, String isbn13,int pages ,Genre genre) {
        if (title == null || title.trim().length() == 0) throw new IllegalArgumentException("book title cannot be null or blank");
        this.id = id;
        this.title = title.trim();
        this.sellingPrice = sellingPrice;
        this.isbn13 = isbn13;
        this.pages = pages;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn13() {
        return isbn13;
    } //LocalDate is immutable

    public Genre getGenre() { return genre; }

    public int getPages() {
        return pages;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
    public int getId() { return id; }

    public void setId(int id) { //TODO: hide id?
        if (this.id == -1) this.id = id;
    } //student heeft enkel bij initialisatie id=-1 en kan dus maar 1 keer overschreven worden

    public Author getAuthor() {return author;}

    public void setAuthor(Author author) {this.author = author;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return  getTitle().equals(book.getTitle()) &&
                getIsbn13().equals(book.getIsbn13());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getIsbn13());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", pages=" + pages +
                ", selling price = EUR" + sellingPrice +
                ", genre:" + genre +
                "}\n\t" + author;
    }
}
