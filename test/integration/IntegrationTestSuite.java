// JUnit
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DBTest.class,
    BookDAOTest.class,
    ComicDAOTest.class,
    MovieDAOTest.class,
    MusicDAOTest.class,
    SeriesDAOTest.class,
})

public class IntegrationTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
