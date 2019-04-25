package stepdefs;

import HttpUtil.constant.HttpConstant;
import api.Api;
import api.ApiCall;
import api.callback.HttpResponseCallback;
import api.endpoint.EndPoint;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.AuthenticatedUser;
import model.NewUserObject;
import util.JSONKeys;
import util.JsonParser;
import util.StateHolder;
import java.util.HashMap;
import static org.junit.Assume.assumeTrue;

/**
 * This is the cucumber class that execute all the userRequestTest.feature scenarios.
 *
 * @author Melchior Vrolijk
 * @see cucumber.api.java8.En
 */
public class UserRequestStepdefs
{
    //region Local instances
    private Object testUserLoginResponse = null;
    private NewUserObject updatedTestUser = null;
    private NewUserObject testAdminObject = null;
    private int updatedTestUserRequestResponseCode = -1;
    private int adminListRetrievingResponseCode = -1;
    private int adminCreationAttemptResponseCode = -1;
    private Object updatedTestUserRequestResponse = null;
    private static final String UPDATED_FIRST_NAME = "TestUpdate";
    //endregion

    //region Test user authenticate by login using username and password
    /**
     * Check if a valid test user credentials are valid
     */
    @Given("^the test user credentials$")
    public void theTestUserCredentials()
    {
        assumeTrue(!StateHolder.TEST_USER_USERNAME.equals(""));
        assumeTrue(!StateHolder.TEST_USER_PASSWORD.equals(""));
    }

    /**
     * Test user attempt to login by using the test user login credential
     */
    @When("^try to login with the given test user credentials$")
    public void tryToLoginWithTheGivenTestUserCredentials()  {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.EMAIL_KEY, StateHolder.TEST_USER_USERNAME);
        requestBody.put(HttpConstant.PASSWORD_KEY,StateHolder.TEST_USER_PASSWORD);

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.LOGIN.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        testUserLoginResponse = response;
                        assumeTrue(!testUserLoginResponse.equals(""));
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed loging in with response code: %s and error: %s",responseCode,error));
                    }
                })
        );
    }

    /**
     * Determine if the test user has successfully logged in
     */
    @Then("^successfully login as a user$")
    public void successfullyLoginAsAUser()  {
        StateHolder.created_user = JsonParser.toAuthenticatedUser(this.testUserLoginResponse.toString());
        assumeTrue(StateHolder.created_user != null && StateHolder.created_user.getID() != -1);
    }

    /**
     * Obtain the test user authorization token
     */
    @Then("^obtain the test user authorization token$")
    public void obtainTheTestUserAuthorizationToken()  {
        assumeTrue(!StateHolder.created_user.getSessionToken().equals(""));
    }
    //endregion

    //region Test user update personal information
    /**
     * Determine if the test user authorization token is valid
     */
    @Given("^the test user authorization token$")
    public void theTestUserAuthorizationToken()
    {
        assumeTrue(!StateHolder.created_user.getSessionToken().equals(""));
    }

    /**
     * Test user create updated test user object
     */
    @When("^creating updated test user object$")
    public void creatingUpdatedTestUserObject()
    {
        this.updatedTestUser = new NewUserObject("testUser@test.com",UPDATED_FIRST_NAME,"UserUpdate","Test user updated","test123");
    }

    /**
     * Test user call /user/update api attempting to update user personal data
     */
    @Then("^test user send a PUT request to /user/update/ including the updated personal data$")
    public void testUserSendAPUTRequestToUserUpdateIncludingTheUpdatedPersonalData()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(JSONKeys.EMAIL,this.updatedTestUser.getEmail());
        requestBody.put(JSONKeys.FIRST_NAME,this.updatedTestUser.getFirstName());
        requestBody.put(JSONKeys.LAST_NAME,this.updatedTestUser.getLastName());
        requestBody.put(JSONKeys.OCCUPATION,this.updatedTestUser.getOccupation());
        requestBody.put(JSONKeys.PASSWORD,this.updatedTestUser.getPassword());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.UPDATE_USER.getValue(),Integer.toString(StateHolder.created_user.getID()))
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        updatedTestUserRequestResponseCode = responseCode;
                        updatedTestUserRequestResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        updatedTestUserRequestResponseCode = responseCode;
                    }
                })
        );
    }

    /**
     * Determine if the test user successfully updated his personal data
     * @param expected_response_code The expected server response code
     */
    @Then("^successfully updated personal data by verify if the server return a response code (\\d+)$")
    public void successfullyUpdatedPersonalDataByVerifyIfTheServerReturnAResponseCode(int expected_response_code)  {
        if (updatedTestUserRequestResponseCode == expected_response_code)
        {
            AuthenticatedUser updatedUser = JsonParser.toAuthenticatedUser(this.updatedTestUserRequestResponse.toString());
            assumeTrue(updatedUser != null && updatedUser.getFirstName().equals(UPDATED_FIRST_NAME));
        }
    }
    //endregion

    //region Test user attempt to retrieve list of admins
    /**
     * Test user attempt to retrieve list of all admins by calling /admin/all api
     */
    @When("^test user send a GET request to /admin/all$")
    public void testUserSendAPOSTRequestToAdminAll()  {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_ADMIN.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        adminListRetrievingResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        adminListRetrievingResponseCode = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Determine if the server successfully blocked test user from retrieving list of all admins
     * @param expected_response_code The expected response code
     */
    @Then("^the server successfully block test user from retrieving list of admins by responding with response code (\\d+) 'unauthorized'$")
    public void theServerSuccessfullyBlockTestUserFromRetrievingListOfAdminsByRespondingWithResponseCodeUnauthorized(int expected_response_code)
    {
        assumeTrue(adminListRetrievingResponseCode == expected_response_code);
    }
    //endregion

    //region Test user attempt to retrieve list of users test
    /**
     * Test user attempt to retrieve list of all users
     */
    @When("^test user send a GET request to /user/all$")
    public void testUserSendAPOSTRequestToUserAll()  {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_USER.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        adminListRetrievingResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        adminListRetrievingResponseCode = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Determine if the server successfully block test user from retrieving list of all users
     * @param expected_response_code The expected response code
     */
    @Then("^the server successfully block test user from retrieving list of users by responding with response code (\\d+) 'unauthorized'$")
    public void theServerSuccessfullyBlockTestUserFromRetrievingListOfUsersByRespondingWithResponseCodeUnauthorized(int expected_response_code)  {
        assumeTrue(adminListRetrievingResponseCode == expected_response_code);
    }
    //endregion

    //region Test user attempt to create new admin user test
    /**
     * Test user create test admin instance
     */
    @When("^test user create a test admin user instance$")
    public void testUserCreateATestAdminUserInstance()  {
        this.testAdminObject = new NewUserObject("testadmin2@test.com","Test","Admin2","Test admin 2","testadmin2");
    }

    /**
     * Test user call /admin/create api attempting to create add a new admin to database
     */
    @Then("^test user send a POST request to /admin/create$")
    public void testUserSendAPOSTRequestToAdminCreate()  {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(JSONKeys.EMAIL,this.testAdminObject.getEmail());
        requestBody.put(JSONKeys.FIRST_NAME,this.testAdminObject.getFirstName());
        requestBody.put(JSONKeys.LAST_NAME,this.testAdminObject.getLastName());
        requestBody.put(JSONKeys.OCCUPATION,this.testAdminObject.getOccupation());
        requestBody.put(JSONKeys.PASSWORD,this.testAdminObject.getPassword());

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.CREATE_ADMIN.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response)
                    {
                        adminCreationAttemptResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        adminCreationAttemptResponseCode = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Determine if the server successfully blocked test user from creating adding new admin to database
     * @param expected_response_code The expected server response
     */
    @Then("^the server successfully block test user from creating new admin by responding with response code (\\d+) 'unauthorized'$")
    public void theServerSuccessfullyBlockTestUserFromCreatingNewAdminByRespondingWithResponseCodeUnauthorized(int expected_response_code)  {
        assumeTrue(adminCreationAttemptResponseCode == expected_response_code);
    }
    //endregion
}
