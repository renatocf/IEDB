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
        final MovieDAO dao = new MovieDAO();
        running(fakeApplication(), new Runnable() {
            public void run() {
                assertEquals("The Lord of the Rings: The Fellowship of the Ring",
                    dao.getByName("Lord").get(0).getName());
            }
        });
        
        /*running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource()).contains("Hello world");
            }
        });*/
    }

}
