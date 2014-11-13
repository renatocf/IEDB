import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class MovieDAOTest {
    
    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void getByName() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                MovieDAO dao = new MovieDAO();
                assertEquals("The Lord of the Rings: The Fellowship of the Ring",
                    dao.getByName("Lord").get(0).getName());
            }
        });
    }
}
