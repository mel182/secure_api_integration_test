import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * This integration test main class
 * @author Melchior Vrolijk
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = {"src/test/resources"},
        glue = {"stepdefs"})
public class CucumberIntegrationTest {
}
