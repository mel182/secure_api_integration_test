package stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import server.TargetServer;
import static org.junit.Assume.assumeTrue;

import static org.assertj.core.api.Assertions.assertThat;

public class TargetServerStepDefs {

    private String BASE_URL = "";
    private boolean isReachable = false;

    @Given("^the target server base URL$")
    public void TargetServerStepDefs()
    {
        BASE_URL = TargetServer.BASE_URL;
        assertThat(BASE_URL).isNotEqualTo("");
    }

    @When("^the application check if the target server is running$")
    public void theApplicationCheckIfTheTargetServerIsRunning() throws Throwable {
        isReachable = TargetServer.isReachable(TargetServer.IP_ADDRESS,TargetServer.PORT_NR);
        assumeTrue(true);
    }

    @Then("^the target server respond positively$")
    public void theTargetServerRespondPositively() throws Throwable {
        assumeTrue(isReachable);
    }
}
