import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class MusicDAOTest {
    
    private MusicDAO dao;

    @Before
    public void setUp() throws Exception{
        running(fakeApplication(), new Runnable() {
            public void run() {
                dao = new MusicDAO();
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
        assertEquals("Lucy in the sky with diamonds", dao.getByName("sky").get(0).getName());
    }
    
    @Test
    public void getAll() {
        assertEquals(1, dao.getAll().size());
        assertEquals("Lucy in the sky with diamonds", dao.getAll().get(0).getName());
    }
}
