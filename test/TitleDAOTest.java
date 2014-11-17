package unit;

import models.TitleDAO;

import org.junit.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class TitleDAOTest {

    private TitleDAO dao;

    @Before
    public void setUp() throws Exception{
        running(fakeApplication(), new Runnable() {
            public void run() {
                dao = new TitleDAO();
            }
        });
    }

    @After
    public void tearDown() throws Exception{
        dao = null;
    }

    @Test
    public void getAll() {
        assertEquals(2, dao.getAll().size());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", 
            dao.getAll().get(0).getName());
        assertEquals("Matrix", dao.getAll().get(1).getName());
    }

    @Test
    public void getByTypeAndName() {
        assertEquals(1, dao.getByTypeAndName("movie", "lord").size());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", 
            dao.getByTypeAndName("movie", "lord").get(0).getName());
    }

    @Test
    public void getByName() {
        assertEquals(1, dao.getByName("lord").size());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring",
            dao.getByName("lord").get(0).getName());
    }
}
