package stepdefs;

import HttpUtil.constant.HttpConstant;
import api.Api;
import api.ApiCall;
import api.callback.HttpResponseCallback;
import api.endpoint.EndPoint;
import cucumber.api.PendingException;
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
    @Given("^test admin login credentials$")
    public void rootUserGetAResponseThatTestUserHasBeenSuccessfullyRemoved() throws Throwable
    {
        assumeTrue(true);
    }

    @Then("^test admin send POST request to /auth/login with test admin login credentials$")
    public void loginAsTestAdmin() throws Throwable
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

    @Then("^successfully logged as test admin$")
    public void checkIfTestAdminIsLoggedInSuccessfully() throws Throwable
    {
        StateHolder.created_admin = JsonParser.toAuthenticatedUser(this.testAdminLoginRequestResponse.toString());
        assumeTrue(StateHolder.created_admin.getID() != -1);
    }

    @Then("^obtain test admin authorization token$")
    public void retrieveTestAdminAuthorizationToken() throws Throwable
    {
        assumeTrue(!StateHolder.created_admin.getSessionToken().equals(""));
    }

    @Then("^obtain test admin user record ID$")
    public void retrieveTestAdminRecordID() throws Throwable
    {
        assumeTrue(StateHolder.created_admin.getID() != -1);
    }
    //endregion

    //region Test admin create test user test
    @When("^test admin create test user object$")
    public void testAdminCreateTestUserObject() throws Throwable
    {
        this.testUserObject = new NewUserObject("testUser@test.com","Test","User","Occupation","test123");
    }

    @Then("^test admin send POST request to /user/create including test user data$")
    public void testAdminSendPOSTRequestToServerToCreateTestUser() throws Throwable
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

    @Then("^successfully created test user$")
    public void determineIfTestUserIsSuccessfullyCreated() throws Throwable
    {
        StateHolder.created_user = JsonParser.toAuthenticatedUser(this.createdTestUserRequestResponse.toString());
        assumeTrue(!StateHolder.created_user.getEmail().equals(""));
    }

    @Then("^obtain test user record ID$")
    public void obtainTestUserRecordID() throws Throwable
    {
        assumeTrue(StateHolder.created_user.getID() != -1);
    }
    //endregion

    //region Test admin retrieve list of all users
    @When("^test admin send GET request to /user/all to retrieve list of all users$")
    public void testAdminRetrieveListOfAllUserTest() throws Throwable
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

    @Then("^successfully retrieved list of users$")
    public void determineIfTestAdminSuccessfullyRetrievedListOfUsers() throws Throwable
    {
        if (this.userListResponse != null) {
            Object json = new JSONTokener(this.userListResponse.toString()).nextValue();
            if (json instanceof JSONArray)
                assumeTrue(true);
        }
    }
    //endregion

    //region Test admin attempt to retrieve list of all admins
    @When("^test admin send GET request to /admin/all to retrieve list of all admins$")
    public void testAdminAttemptToRetrieveListOfAdmins() throws Throwable
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

    @Then("^server successfully block such attempt by sending response code (\\d+) 'unauthorized'$")
    public void determineIfServerBlockAdminListRequest(int response_code) throws Throwable
    {
        assumeTrue(this.adminListResponse == response_code);
    }
    //endregion

    //region Test admin attempt to create new admin
    @When("^test admin create new admin user object$")
    public void testAdminCreateNewAdminObject() throws Throwable
    {
        this.testAdminObject = new NewUserObject("testadmin2@test.com","Test","Admin2","Test admin 2","testadmin2");
    }

    @When("^test admin send POST request to /admin/create including the new test admin user object$")
    public void testAdminAttemptToCreateNewAdmin() throws Throwable
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

    @Then("^server successfully block such attempt to create new admin by sending response code (\\d+) 'unauthorized'$")
    public void serverSuccessfullyBlockSuchAttemptToCreateNewAdminBySendingResponseCodeUnauthorized(int expected_response_code) throws Throwable {
        assumeTrue(this.admin2AttemptResponseCode == expected_response_code);
    }
    //endregion

    //region Test admin attemp to update test user personal information test
    @When("^test admin created updated object of the test user$")
    public void testAdminCreateUpdatedTestUserObject() throws Throwable
    {
        this.updatedTestUserObject = new NewUserObject("testUser@test.com","Test123","User123","Occupation123","test123");
    }

    @When("^test admin send PUT request to /user/update including the test user record ID and updated values$")
    public void testAdminAttemptToUpdateTestUser() throws Throwable
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

    @Then("^server successfully block test user update task since by sending response code (\\d+) 'unauthorized'$")
    public void serverSuccessfullyBlockTestUserUpdateTaskSinceBySendingResponseCodeUnauthorized(int expected_response_code) throws Throwable
    {
        assumeTrue(updateTestUserAttemptResponseCode == expected_response_code);
    }
    //endregion

    //region Test admin attempt to remove admin from database
    @When("^test admin send DELETE request to /admin/ including the test user record ID$")
    public void testAdminAttemptToDeletedAdminFromDatabase() throws Throwable
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

    @Then("^server successfully block delete admin task by sending response code (\\d+) 'unauthorized'$")
    public void testAdminAttemptToDeletedAdminFromDatabase(int expected_response_code) throws Throwable
    {
        assumeTrue(deleteTestAdminRequestResponseCode == expected_response_code);
    }
    //endregion

    //region Test admin remove user from database
    @When("^test admin send DELETE request to /user/ including the test user record ID$")
    public void testAdminDeleteUserFromDatabase() throws Throwable
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

    @Then("^test admin successfully deleted test user from database by checking respond code equal to (\\d+)$")
    public void testAdminSuccessfullyDeletedTestUserFromDatabaseByCheckingRespondCodeEqualTo(int expected_responds_code) throws Throwable {
        assumeTrue(deleteTestUserRequestResponseCode == expected_responds_code);
    }

    @Then("^determine if test admin successfully deleted test user from database$")
    public void determineIfTestAdminHasSuccessfullyRemovedTestUserFromDatabase() throws Throwable
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

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
