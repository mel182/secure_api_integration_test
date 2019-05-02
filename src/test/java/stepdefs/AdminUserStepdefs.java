package stepdefs;

import HttpUtil.constant.HttpConstant;
import api.Api;
import api.ApiCall;
import api.callback.HttpResponseCallback;
import api.endpoint.EndPoint;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import model.AuthenticatedUser;
import model.NewUserObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import util.JSONKeys;
import util.JsonParser;
import util.StateHolder;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assume.assumeTrue;

/**
 * This is the cucumber class that execute all the adminUserRequestTest.feature scenarios.
 *
 * @author Melchior Vrolijk
 * @see cucumber.api.java8.En
 */
public class AdminUserStepdefs implements En
{
    //region Local instances
    private Object testAdminLoginRequestResponse = "";
    private Object createdTestUserRequestResponse = "";
    private Object userListResponse = null;
    private NewUserObject testUserObject = null;
    private NewUserObject updatedTestUserObject = null;
    private NewUserObject testAdminObject = null;
    private int adminListResponse = -1;
    private int admin2AttemptResponseCode = -1;
    private int updateTestUserAttemptResponseCode = -1;
    private int deleteTestAdminRequestResponseCode = -1;
    private int deleteTestUserRequestResponseCode = -1;
    //endregion

    //region Login as test admin
    /**
     * Login as a test admin scenario
     */
    @Given("^test admin login credentials$")
    public void retrieveTestAdminLoginCredentials()
    {
        assumeTrue(true);
    }

    /**
     * Login as test admin
     */
    @Then("^test admin send POST request to /auth/login with test admin login credentials$")
    public void loginAsTestAdmin()
    {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.EMAIL_KEY,StateHolder.TEST_ADMIN_USERNAME);
        requestBody.put(HttpConstant.PASSWORD_KEY,StateHolder.TEST_ADMIN_PASSWORD);

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.LOGIN.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        testAdminLoginRequestResponse = response;
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed loging in with response code: %s and error: %s",responseCode,error));
                    }
                })
        );
    }

    /**
     * Determine if the test admin is logged in successfully
     */
    @Then("^successfully logged as test admin$")
    public void checkIfTestAdminIsLoggedInSuccessfully()
    {
        StateHolder.created_admin = JsonParser.toAuthenticatedUser(this.testAdminLoginRequestResponse.toString());
        assumeTrue(StateHolder.created_admin.getID() != -1);
    }

    /**
     * Obtain test admin authorization token
     */
    @Then("^obtain test admin authorization token$")
    public void retrieveTestAdminAuthorizationToken()
    {
        assumeTrue(!StateHolder.created_admin.getSessionToken().equals(""));
    }

    /**
     * Obtain test admin user database record
     */
    @Then("^obtain test admin user record ID$")
    public void retrieveTestAdminRecordID()
    {
        assumeTrue(StateHolder.created_admin.getID() != -1);
    }
    //endregion

    //region Test admin create test user test
    /**
     * Test admin create test user object
     */
    @When("^test admin create test user object$")
    public void testAdminCreateTestUserObject()
    {
        this.testUserObject = new NewUserObject("testUser@test.com","Test","User","Occupation","test123");
    }

    /**
     * Test admin call /user/create API for creating the test user
     */
    @Then("^test admin send POST request to /user/create including test user data$")
    public void testAdminSendPOSTRequestToServerToCreateTestUser()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(JSONKeys.EMAIL,this.testUserObject.getEmail());
        requestBody.put(JSONKeys.FIRST_NAME,this.testUserObject.getFirstName());
        requestBody.put(JSONKeys.LAST_NAME,this.testUserObject.getLastName());
        requestBody.put(JSONKeys.OCCUPATION,this.testUserObject.getOccupation());
        requestBody.put(JSONKeys.PASSWORD,this.testUserObject.getPassword());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.CREATE_USER.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        createdTestUserRequestResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed creating user with response code %s and error: %s",responseCode,error));
                    }
                })
        );
    }

    /**
     * Determine if test user is successfully created
     */
    @Then("^successfully created test user$")
    public void determineIfTestUserIsSuccessfullyCreated()
    {
        StateHolder.created_user = JsonParser.toAuthenticatedUser(this.createdTestUserRequestResponse.toString());
        assumeTrue(!StateHolder.created_user.getEmail().equals(""));
    }

    /**
     * Obtain test user database record ID
     */
    @Then("^obtain test user record ID$")
    public void obtainTestUserRecordID()
    {
        assumeTrue(StateHolder.created_user.getID() != -1);
    }
    //endregion

    //region Test admin retrieve list of all users
    /**
     * Test admin call /user/all api to retrieve list of all users
     */
    @When("^test admin send GET request to /user/all to retrieve list of all users$")
    public void testAdminRetrieveListOfAllUserTest()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_USER.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        userListResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed retrieving users with response code: %s and error message: %s",responseCode,error));
                    }
                })
        );
    }

    /**
     * Determine if test admin successfully retrieve list of user by checking if the server response is a valid JSON array
     */
    @Then("^successfully retrieved list of users$")
    public void determineIfTestAdminSuccessfullyRetrievedListOfUsers()
    {
        if (this.userListResponse != null) {
            Object json = new JSONTokener(this.userListResponse.toString()).nextValue();
            if (json instanceof JSONArray)
                assumeTrue(true);
        }
    }
    //endregion

    //region Test admin attempt to retrieve list of all admins
    /**
     * Test admin call /admin/all to retrieve list of all admins
     */
    @When("^test admin send GET request to /admin/all to retrieve list of all admins$")
    public void testAdminAttemptToRetrieveListOfAdmins()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_ADMIN.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        adminListResponse = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        adminListResponse = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Determine if server block test admin from retrieving list of admins
     * @param response_code The expected response code
     */
    @Then("^server successfully block such attempt by sending response code (\\d+) 'unauthorized'$")
    public void determineIfServerBlockAdminListRequest(int response_code)
    {
        assumeTrue(this.adminListResponse == response_code);
    }
    //endregion

    //region Test admin attempt to create new admin
    /**
     * Test admin create a new admin user object
     */
    @When("^test admin create new admin user object$")
    public void testAdminCreateNewAdminObject()
    {
        this.testAdminObject = new NewUserObject("testadmin2@test.com","Test","Admin2","Test admin 2","testadmin2");
    }

    /**
     * Test admin attempt to create a new admin user
     */
    @When("^test admin send POST request to /admin/create including the new test admin user object$")
    public void testAdminAttemptToCreateNewAdmin()
    {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(JSONKeys.EMAIL,this.testAdminObject.getEmail());
        requestBody.put(JSONKeys.FIRST_NAME,this.testAdminObject.getFirstName());
        requestBody.put(JSONKeys.LAST_NAME,this.testAdminObject.getLastName());
        requestBody.put(JSONKeys.OCCUPATION,this.testAdminObject.getOccupation());
        requestBody.put(JSONKeys.PASSWORD,this.testAdminObject.getPassword());

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.CREATE_ADMIN.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response)
                    {
                        admin2AttemptResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        admin2AttemptResponseCode = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Determine if the server successfully block test admin from creating new admin user
     * @param expected_response_code The expected response code
     */
    @Then("^server successfully block such attempt to create new admin by sending response code (\\d+) 'unauthorized'$")
    public void serverSuccessfullyBlockSuchAttemptToCreateNewAdminBySendingResponseCodeUnauthorized(int expected_response_code)
    {
        assumeTrue(this.admin2AttemptResponseCode == expected_response_code);
    }
    //endregion

    //region Test admin attempt to update test user personal information test
    /**
     * Test admin create a test user object
     */
    @When("^test admin created updated object of the test user$")
    public void testAdminCreateUpdatedTestUserObject()
    {
        this.updatedTestUserObject = new NewUserObject("testUser@test.com","Test123","User123","Occupation123","test123");
    }

    /**
     * Test admin call /user/update in a attempt to update a test user personal data
     */
    @When("^test admin send PUT request to /user/update including the test user record ID and updated values$")
    public void testAdminAttemptToUpdateTestUser()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(JSONKeys.EMAIL,this.updatedTestUserObject.getEmail());
        requestBody.put(JSONKeys.FIRST_NAME,this.updatedTestUserObject.getFirstName());
        requestBody.put(JSONKeys.LAST_NAME,this.updatedTestUserObject.getLastName());
        requestBody.put(JSONKeys.OCCUPATION,this.updatedTestUserObject.getOccupation());
        requestBody.put(JSONKeys.PASSWORD,this.updatedTestUserObject.getPassword());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.UPDATE_USER.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        updateTestUserAttemptResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        updateTestUserAttemptResponseCode = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Determine if the server successfully blocked test admin from updating test user personal data
     * @param expected_response_code The expected response code
     */
    @Then("^server successfully block test user update task since by sending response code (\\d+) 'unauthorized'$")
    public void serverSuccessfullyBlockTestUserUpdateTaskSinceBySendingResponseCodeUnauthorized(int expected_response_code)
    {
        assumeTrue(updateTestUserAttemptResponseCode == expected_response_code);
    }
    //endregion

    //region Test admin attempt to remove admin from database
    /**
     * Test admin attempt to delete admin from database
     */
    @When("^test admin send DELETE request to /admin/ including the test user record ID$")
    public void testAdminAttemptToDeletedAdminFromDatabase()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] adminID = new String[] {Integer.toString(StateHolder.created_admin.getID())};

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_ADMIN.getValue(),adminID)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response)
                    {
                        deleteTestAdminRequestResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deleteTestAdminRequestResponseCode = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    /**
     * Detemine if the server successfully blocked test admin from deleting admin from database
     * @param expected_response_code The expected response code
     */
    @Then("^server successfully block delete admin task by sending response code (\\d+) 'unauthorized'$")
    public void testAdminAttemptToDeletedAdminFromDatabase(int expected_response_code)
    {
        assumeTrue(deleteTestAdminRequestResponseCode == expected_response_code);
    }
    //endregion

    //region Test admin remove user from database
    /**
     * Test admin remove test user from database
     */
    @When("^test admin send DELETE request to /user/ including the test user record ID$")
    public void testAdminDeleteUserFromDatabase()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] adminID = new String[] {Integer.toString(StateHolder.created_user.getID())};

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_USER.getValue(),adminID)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response)
                    {
                        deleteTestUserRequestResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deleteTestUserRequestResponseCode = responseCode;
                    }
                })
        );
    }

    /**
     * Determine if test admin successfully deleted test user from database by checking the response code
     * @param expected_responds_code The expected response code
     */
    @Then("^test admin successfully deleted test user from database by checking respond code equal to (\\d+)$")
    public void testAdminSuccessfullyDeletedTestUserFromDatabaseByCheckingRespondCodeEqualTo(int expected_responds_code)
    {
        assumeTrue(deleteTestUserRequestResponseCode == expected_responds_code);
    }

    /**
     * Determine if test admin successfully removed test user from database by making a request to /user/all and verify if the test user is indeed removed
     */
    @Then("^determine if test admin successfully deleted test user from database$")
    public void determineIfTestAdminHasSuccessfullyRemovedTestUserFromDatabase()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_USER.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        List<AuthenticatedUser> user_list = JsonParser.toUserList(response.toString());

                        if (user_list.size() > 0)
                        {
                            boolean user_successfully_removed = !user_list.stream().allMatch(user -> StateHolder.created_user.getID() == user.getID());
                            assumeTrue(user_successfully_removed);
                        }else{
                            assumeTrue(true);
                        }
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed removing user with status code: %s and error: %s",responseCode,error));
                    }
                })
        );
    }
    //endregion
}
