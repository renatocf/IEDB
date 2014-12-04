// JUnit
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import play.libs.F.Callback;

// Fake application
import play.test.*;
import static play.test.Helpers.*;

public class IndexPageTest extends WithApplication{
	
	@Test
	public void runInBrowser() {
	    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
	        public void invoke(TestBrowser browser) {
	            browser.goTo("http://localhost:3333");
	            assertEquals("IEDB - Home", browser.$("#title").getText());
	        }
	    });
	}

	@Test
	public void visualizeTitleStartingAtIndex() {
	    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
	        public void invoke(TestBrowser browser) {
	            browser.goTo("http://localhost:3333");
	            
	            browser.$("#search_input").get(0).text("matrix");
	            browser.$("#search_submit").click();
	            assertEquals("IEDB - Search Results", browser.$("#title").getText());
	            browser.$("#acceptance_test").click();
	            assertEquals("IEDB - Matrix", browser.$("#title").getText());
	        }
	    });
	}
}