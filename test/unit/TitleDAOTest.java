package unit; /**
 * Created by ruan0408 on 11/11/14.
 */

import models.TitleDAO;
import org.junit.*;
import static org.junit.Assert.*;

public class TitleDAOTest {

    private TitleDAO dao;

    @Before
    public void setUp() throws Exception{
        dao = new TitleDAO();
    }

    @After
    public void tearDown() throws Exception{
        dao = null;
    }

    @Test
    public void getAll(){
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", dao.getAll().get(0).getName());
    }


}
