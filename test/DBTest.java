import org.junit.Test;
/* import org.springframework.util.Assert; */
import static org.junit.Assert.*;

import javax.sql.DataSource;
import java.sql.Connection;

import play.db.DB;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class DBTest {

    @Test
    public void testGetDataSource() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                DataSource ds = DB.getDataSource();
                assertNotNull(ds);
            }
        });
    }

    @Test
    public void testGetConnection() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Connection conn = DB.getConnection();
                assertNotNull(conn);
            }
        });
    }
}
