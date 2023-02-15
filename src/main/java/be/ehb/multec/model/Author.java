package be.ehb.multec.model;

import java.time.LocalDate;
import java.util.Objects;

public class Author {
    private int id;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;

    public Author(String firstName, String lastName, LocalDate birthday) {
        this(-1, firstName, lastName, birthday);
    }

    public Author(int id, String firstName, String lastName, LocalDate birthday) {
        if (firstName == null || firstName.trim().length() == 0) throw new IllegalArgumentException("student name cannot be null or blank");
        this.id = id;
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.birthday = birthday; //LocalDate is immutable
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    } //LocalDate is immutable

    public int getId() { return id; }

    public void setId(int id) { //TODO: hide id?
        if (this.id == -1) this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return  getFirstName().equals(author.getFirstName()) &&
                getLastName().equals(author.getLastName()) &&
                getBirthday().equals(author.getBirthday());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBirthday(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
