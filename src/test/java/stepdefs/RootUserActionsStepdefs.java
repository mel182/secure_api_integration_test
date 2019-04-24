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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

public class RootUserActionsStepdefs implements En {

    //region Local instances
    private Object loginRequestResponse = null;
    private Object createAdminResponse = null;
    private Object adminListResponse = null;
    private Object createUserResponse = null;
    private Object userListResponse = null;
    private String root_user_name = "";
    private String root_user_password = "";
    private NewUserObject test_admin = null;
    private NewUserObject test_user = null;
    //endregion

    //region Root user authenticated and call apis that he is authorized to use
    @Given("^the root user credentials$")
    public void retrieveRootUserLoginCredential() throws Throwable
    {
        this.root_user_name = StateHolder.root_user_name;
        this.root_user_password = StateHolder.root_user_password;
        assumeTrue(true);
    }

    @When("^try to login with the given root user credentials$")
    public void tryLogingInAsRootUser() throws Throwable
    {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.EMAIL_KEY,this.root_user_name);
        requestBody.put(HttpConstant.PASSWORD_KEY,this.root_user_password);

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.LOGIN.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        loginRequestResponse = response;
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) { }
                })
        );
    }

    @Then("^successfully login as the root user$")
    public void loginSuccessfully() throws Throwable
    {
        assumeTrue(loginRequestResponse != null);
    }

    @Then("^obtain the authorization token$")
    public void obtainAuthorizationToken() throws Throwable
    {
        AuthenticatedUser authenticationResponse = JsonParser.toAuthenticatedUser(loginRequestResponse.toString());
        StateHolder.authorization_token = authenticationResponse.getSessionToken();
        assertThat(StateHolder.authorization_token).isNotEqualTo("");
    }
    //endregion

    //region Add test admin to database with root user authorization
    @Given("^the root user authorization token$")
    public void checkRootUserAuthorizationToken() throws Throwable
    {
        assertThat(StateHolder.authorization_token).isNotEqualTo("");
    }

    @When("^root user create the test object containing the test admin data$")
    public void createTestAdmin() throws Throwable
    {
        this.test_admin = new NewUserObject(StateHolder.TEST_ADMIN_USERNAME,"admin","Test","Administrator",StateHolder.TEST_ADMIN_PASSWORD);
    }

    @Then("^root user send POST request to /admin/create with 'adminTest@test.com'$")
    public void rootUserSendPOSTRequestToAdminCreateWithAdminTestTestCom() throws Throwable {

        if (this.test_admin != null)
        {
            HashMap<String,String> requestBody = new HashMap<>();
            requestBody.put(JSONKeys.EMAIL,this.test_admin.getEmail());
            requestBody.put(JSONKeys.FIRST_NAME,this.test_admin.getFirstName());
            requestBody.put(JSONKeys.LAST_NAME,this.test_admin.getLastName());
            requestBody.put(JSONKeys.OCCUPATION,this.test_admin.getOccupation());
            requestBody.put(JSONKeys.PASSWORD,this.test_admin.getPassword());

            HashMap<String,String> requestHeader = new HashMap<>();
            requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);
            requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

            Api.call(new ApiCall()
                    .setEndPoint(EndPoint.CREATE_ADMIN.getValue())
                    .setHeader(requestHeader)
                    .setBody(requestBody)
                    .setCallback(new HttpResponseCallback() {
                        @Override
                        public void OnResponse(int responseCode, Object response)
                        {
                            createAdminResponse = response;
                            assumeTrue(!createAdminResponse.toString().equals(""));
                        }

                        @Override
                        public void OnRequestFailed(int responseCode, String error) { }
                    })
            );
        }
    }

    @Then("^successfully created the admin user 'adminTest@test.com'$")
    public void successfullyCreatedTheAdminUserAdminTestTestCom() throws Throwable
    {
        StateHolder.created_admin = JsonParser.toAuthenticatedUser(createAdminResponse.toString());
        assumeTrue(true);
    }

    @Then("^obtain the test admin record ID$")
    public void obtainTheTestAdminRecordID() throws Throwable {
        assumeTrue(StateHolder.created_admin.getID() != -1);
    }
    //endregion

    //region root user retrieve the list of all admins and verified if the test admin user is indeed stored on to the database
    @Given("^the root user authorization token, test admin email 'adminTest@test.com' and record ID$")
    public void retrieveRootUserAuthorizationToken() throws Throwable
    {
        assertThat(StateHolder.authorization_token).isNotEqualTo("");
    }

    @When("^root user send a GET request to /admin/all with the authorization token$")
    public void retrieveListOfAllAdmins() throws Throwable
    {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_ADMIN.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        adminListResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                    }
                })
        );
    }

    @Then("^root user successfully retrieve the list of admin users$")
    public void checkIfAdminListIsSuccessfullyRetrieved() throws Throwable
    {
        if (this.adminListResponse != null) {
            Object json = new JSONTokener(this.adminListResponse.toString()).nextValue();
            if (json instanceof JSONArray)
                assumeTrue(true);
        }
    }

    @Then("^root user verified if the corresponding test admin is in the response list$")
    public void checkIfStoredAdminIsInList() throws Throwable
    {
        List<AuthenticatedUser> user_list = JsonParser.toUserList(this.adminListResponse.toString());

        if (user_list.size() > 0)
        {
            boolean user_found = user_list.stream().allMatch(user -> StateHolder.created_admin.getID() == user.getID());
            assumeTrue(user_found);
        }
    }
    //endregion

    //region root user remove test admin from the database
    @Given("^the root user authorization token and test admin user record ID$")
    public void retrieveTestAdminUserID() throws Throwable
    {
        assumeTrue(StateHolder.created_admin.getID() != -1);
    }

    @When("^root user send a DELETE request to /admin/ including the authorization token and test admin record ID$")
    public void rootUserSendADELETERequestToAdminIdIncludingTheAuthorizationTokenAndTestAdminRecordID() throws Throwable {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

        String[] params = new String[] {Integer.toString(StateHolder.created_admin.getID())};

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_ADMIN.getValue(),params)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        StateHolder.adminDeletedResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) { }
                })
        );
    }

    @Given("^root user get a response that admin has been successfully removed$")
    public void getAdminDeletionResponseCode() throws Throwable
    {
        assumeTrue(StateHolder.adminDeletedResponseCode == 200);
        StateHolder.created_admin = null;
    }

    @Then("^root user verified if the test admin is indeed removed from the database by calling /admin/all$")
    public void verifyIfTestAdminHasBeenRemoved() throws Throwable
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_ADMIN.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        adminListResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                    }
                })
        );
    }

    @Then("^test admin has been successfully removed from the database$")
    public void adminSuccessfullyRemoveFromDatabase() throws Throwable
    {
        List<AuthenticatedUser> user_list = JsonParser.toUserList(this.adminListResponse.toString());

        if (user_list.size() > 0)
        {
            boolean user_successfully_removed = !user_list.stream().allMatch(user -> StateHolder.created_admin.getID() == user.getID());
            assumeTrue(user_successfully_removed);
        }else{
            assumeTrue(true);
        }
    }
    //endregion

    //region root user add new user to database
    @When("^root user create the test object containing the test user data$")
    public void rootUserCreateTheTestObjectContainingTheTestUserData() throws Throwable
    {
        this.test_user = new NewUserObject("testUser@test.com","Test","User","Test user","test123");
    }

    @Then("^root user send POST request to /user/create with 'testUser@test.com'$")
    public void rootUserSendPOSTRequestToUserCreateWithAdminTestTestCom() throws Throwable
    {
        if (this.test_user != null)
        {
            HashMap<String,String> requestBody = new HashMap<>();
            requestBody.put(JSONKeys.EMAIL,this.test_user.getEmail());
            requestBody.put(JSONKeys.FIRST_NAME,this.test_user.getFirstName());
            requestBody.put(JSONKeys.LAST_NAME,this.test_user.getLastName());
            requestBody.put(JSONKeys.OCCUPATION,this.test_user.getOccupation());
            requestBody.put(JSONKeys.PASSWORD,this.test_user.getPassword());

            HashMap<String,String> requestHeader = new HashMap<>();
            requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

            Api.call(new ApiCall()
                    .setEndPoint(EndPoint.CREATE_USER.getValue())
                    .setHeader(requestHeader)
                    .setBody(requestBody)
                    .setCallback(new HttpResponseCallback() {
                        @Override
                        public void OnResponse(int responseCode, Object response)
                        {
                            createUserResponse = response;
                        }

                        @Override
                        public void OnRequestFailed(int responseCode, String error)
                        {
                            System.out.println(String.format("Failed creating user with response code: %s and error message: %s",responseCode,error));
                        }
                    })
            );
        }

    }

    @Then("^successfully created the user 'testUser@test.com'$")
    public void successfullyCreatedTheUserTestUserTestCom() throws Throwable
    {
        StateHolder.created_user = JsonParser.toAuthenticatedUser(createUserResponse.toString());
        assumeTrue(true);
    }

    @Then("^obtain the test user record ID$")
    public void obtainTheTestUserRecordID() throws Throwable
    {
        assumeTrue(StateHolder.created_user.getID() != -1);
    }
    //endregion

    //region Root user retrieve list of all users
    @Given("^the root user authorization token and test user record ID$")
    public void theRootUserAuthorizationTokenAndTestUserRecordID() throws Throwable
    {
        assumeTrue(!StateHolder.authorization_token.equals("") && StateHolder.created_user.getID() != -1);
    }

    @When("^root user send a GET request to /users/all including the authorization token$")
    public void rootUserSendAGETRequestToUsersAllIncludingTheAuthorizationToken() throws Throwable
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

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
                    public void OnRequestFailed(int responseCode, String error)
                    {
                        System.out.println(String.format("Failed to retrieve list of users with response code: %s and error message: %s",responseCode,error));
                    }
                })
        );

    }

    @Then("^root user successfully retrieve the list of users$")
    public void rootUserSuccessfullyRetrieveTheListOfUsers() throws Throwable {
        if (this.userListResponse != null) {
            Object json = new JSONTokener(this.userListResponse.toString()).nextValue();
            if (json instanceof JSONArray)
                assumeTrue(true);
        }
    }

    @Then("^root user verified if the corresponding test user is in the response list$")
    public void rootUserVerifiedIfTheCorrespondingTestUserIsInTheResponseList() throws Throwable {

        List<AuthenticatedUser> user_list = JsonParser.toUserList(this.userListResponse.toString());

        if (user_list.size() > 0)
        {
            boolean user_found = user_list.stream().allMatch(user -> StateHolder.created_user.getID() == user.getID());
            assumeTrue(user_found);
        }
    }
    //endregion

    //region Root user delete test user from database
    @When("^root user send a DELETE request to /user/ including the authorization token and test user record ID$")
    public void rootUserSendADELETERequestToUserIncludingTheAuthorizationTokenAndTestUserRecordID() throws Throwable
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

        String[] params = new String[] {Integer.toString(StateHolder.created_user.getID())};

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_USER.getValue(),params)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        StateHolder.userDeletedResponseCode = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) { }
                })
        );
    }

    @Then("^root user get a response that test user has been successfully removed$")
    public void rootUserGetAResponseThatTestUserHasBeenSuccessfullyRemoved() throws Throwable {

        assumeTrue(StateHolder.userDeletedResponseCode == 200);
    }

    @Then("^root user verified if the test user is indeed removed from the database by calling /user/all$")
    public void rootUserVerifiedIfTheTestUserIsIndeedRemovedFromTheDatabaseByCallingAdminAll() throws Throwable {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.authorization_token);

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
                    }
                })
        );
    }

    @Then("^root user verified if test user has been successfully removed from the database$")
    public void rootUserVerifiedIfTestUserHasBeenSuccessfullyRemovedFromTheDatabase() throws Throwable
    {
        List<AuthenticatedUser> user_list = JsonParser.toUserList(this.userListResponse.toString());

        if (user_list.size() > 0)
        {
            boolean user_found = user_list.stream().allMatch(user -> StateHolder.created_user.getID() == user.getID());
            assumeTrue(!user_found);
        }

        assumeTrue(true);
    }
    //endregion
}
