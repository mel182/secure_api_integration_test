package stepdefs;

import api.Api;
import api.ApiCall;
import api.callback.HttpResponseCallback;
import api.endpoint.EndPoint;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assume.assumeTrue;

/**
 * This is the cucumber class that execute all the unAuthorizedclientRequest.feature scenarios.
 *
 * @author Melchior Vrolijk
 * @see cucumber.api.java8.En
 */
public class UnAuthorizedClientStepDefs implements En {

    //region Local instances
    private int unauthorizedUserStatusCodeResponse = -1;
    //endregion

    //region Client try to make a request to an API which requires authorization token
    /**
     * Unauthorized client call /admin/all api attempting to retrieving list of admin
     */
    @When("^the client calls api without an authorization header$")
    public void clientCallsAdminListGET_Api()  {

        Api.call(new ApiCall()
            .setEndPoint(EndPoint.ALL_ADMIN.getValue())
            .setCallback(new HttpResponseCallback() {
                @Override
                public void OnResponse(int responseCode, Object response) {
                    unauthorizedUserStatusCodeResponse = responseCode;
                }

                @Override
                public void OnRequestFailed(int responseCode, String error) {
                    unauthorizedUserStatusCodeResponse = responseCode;
                }
            })
        );
    }

    /**
     * Determine if client successfully got a bad request response code from the server
     * @param expected_response_code The expected response code
     */
    @Then("^the client receives http status (\\d+) 'bad request' response$")
    public void theClientReceivesHttpStatusBadRequestResponse(int expected_response_code)
    {
        assumeTrue(this.unauthorizedUserStatusCodeResponse == expected_response_code);
    }
    //endregion
}
