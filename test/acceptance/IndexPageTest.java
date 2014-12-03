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
	            //assertThat(, isEqualTo();
	            //browser.$("a").click();
	            //assertEquals(browser.url(), "http://localhost:3333/login");
	            //assertThat(, isEqualTo());
	        }
	    });
	}

	@Test
	public void visualizeTitleStartingAtIndex() {
	    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
	        public void invoke(TestBrowser browser) {
	            browser.goTo("http://localhost:3333");
	            
	            //assertThat(, isEqualTo();
	            browser.$("#search_input").get(0).text("matrix");
	            browser.$("#search_submit").click();
	            assertEquals("IEDB - Search Results", browser.$("#title").getText());
	            browser.$("#acceptance_test").click();
	            assertEquals("IEDB - Matrix", browser.$("#title").getText());
	            //assertEquals(browser.url(), "http://localhost:3333/login");
	            //assertThat(, isEqualTo());
	        }
	    });
	}
}