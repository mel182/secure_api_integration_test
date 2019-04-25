package stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import server.TargetServer;
import static org.junit.Assume.assumeTrue;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is the cucumber class that execute all the targetServerReachability.feature scenarios.
 *
 * @author Melchior Vrolijk
 * @see cucumber.api.java8.En
 */
public class TargetServerStepDefs {

    //region Local instances
    private String BASE_URL = "";
    private boolean isReachable = false;
    //endregion

    //region Check if the target server is running
    /**
     * Retrieve target server base URL
     */
    @Given("^the target server base URL$")
    public void SetTargetServerBaseURL()
    {
        BASE_URL = TargetServer.BASE_URL;
        assertThat(BASE_URL).isNotEqualTo("");
    }

    /**
     * Determine if the target server is running
     */
    @When("^the application check if the target server is running$")
    public void theApplicationCheckIfTheTargetServerIsRunning() {
        isReachable = TargetServer.isReachable(TargetServer.IP_ADDRESS,TargetServer.PORT_NR);
        assumeTrue(true);
    }

    /**
     * Determine if the target server is responding
     */
    @Then("^the target server respond positively$")
    public void theTargetServerRespondPositively() {
        assumeTrue(isReachable);
    }
    //endregion
}
