package be.ehb.multec.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Booktester {
    @Test
    public void createBook(){
        Book b = new Book("Letrange",9.90,"9999999999999",115,Genre.CLASSICS);
        System.out.println(b);
    }

    @Test
    public void createBookWithIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(" \t\r\n", 9.90,"9999999999999",115,Genre.CLASSICS);
        });
    }
}
