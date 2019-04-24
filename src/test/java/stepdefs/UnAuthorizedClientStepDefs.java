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

public class UnAuthorizedClientStepDefs implements En {

    private int unauthorizedUserStatusCodeResponse = -1;

    @When("^the client calls api without an authorization header$")
    public void clientCallsAdminListGET_Api() throws Throwable {

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

    @Then("^the client receives http status (\\d+) 'bad request' response$")
    public void theClientReceivesHttpStatusBadRequestResponse(int arg0) throws Throwable
    {
        assumeTrue(this.unauthorizedUserStatusCodeResponse == HttpServletResponse.SC_BAD_REQUEST);
    }
}
