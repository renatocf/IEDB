import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class ComicDAOTest {
    
    private ComicDAO dao;

    @Before
    public void setUp() throws Exception{
        running(fakeApplication(), new Runnable() {
            public void run() {
                dao = new ComicDAO();
            }
        });
    }

    @After
    public void tearDown() throws Exception{
        dao = null;
    }

    @Test
    public void getByName() {
        assertEquals(1, dao.getByName("Superman").size());
        assertEquals("Superman", dao.getByName("Superman").get(0).getName());
    }
    
    @Test
    public void getAll() {
        assertEquals(1, dao.getAll().size());
        assertEquals("Superman", dao.getAll().get(0).getName());
    }
}
