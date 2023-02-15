package be.ehb.multec.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class AuthorTester {
    @Test
    public void createAuthor(){
        Author a = new Author("Albert","Camus",LocalDate.of(1913,7,11));
        System.out.println(a);
    }
}
