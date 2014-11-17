import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class MovieDAOTest {
    
    private MovieDAO dao;

    @Before
    public void setUp() throws Exception{
        running(fakeApplication(), new Runnable() {
            public void run() {
                dao = new MovieDAO();
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
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", dao.getByName("Lord").get(0).getName());
    }
    
    @Test
    public void getAll() {
        assertEquals(2, dao.getAll().size());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", dao.getAll().get(0).getName());
        assertEquals("Matrix", dao.getAll().get(1).getName());
    }
    
}
