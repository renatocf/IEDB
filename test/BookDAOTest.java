import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class BookDAOTest {
    
    private BookDAO dao;

    @Before
    public void setUp() throws Exception{
        running(fakeApplication(), new Runnable() {
            public void run() {
                dao = new BookDAO();
            }
        });
    }

    @After
    public void tearDown() throws Exception{
        dao = null;
    }

    @Test
    public void getByName() {
        assertEquals(1, dao.getAll().size());
        assertEquals("Harry Potter and the Philosopher's Stone", dao.getByName("Potter").get(0).getName());
    }
    
    @Test
    public void getAll() {
        assertEquals(1, dao.getAll().size());
        assertEquals("Harry Potter and the Philosopher's Stone", dao.getAll().get(0).getName());
    }
}
