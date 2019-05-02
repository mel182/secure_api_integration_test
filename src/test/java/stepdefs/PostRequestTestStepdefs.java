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
import model.RequestPost;
import util.JSONKeys;
import util.JsonParser;
import util.StateHolder;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assume.assumeTrue;

public class PostRequestTestStepdefs implements En {

    //region Local instances
    //region Root user local instances
    private NewUserObject testUser2Object = null;
    private RequestPost requestPostRootUser = null;
    private int getAllPostResponseCodeRootUser = -1;
    private int getPostItemResponseCodeRootUser = -1;
    private Object postRequestRawResponseRootUser = null;
    private int postRequestResponseCodeRootUser = -1;
    private int deleteTestUser2ResponseCodeRootUser = -1;
    private RequestPost rootUpdatedPost = null;
    private int updatePostItemResponseCodeRootUser = -1;
    private int deletePostItemResponseCodeRootUser = -1;
    //endregion

    //region Admin local instances
    private RequestPost requestPostAdmin = null;
    private Object postRequestRawResponseAdmin = null;
    private int postRequestResponseCodeAdmin = -1;
    private int getAllPostResponseCodeAdmin = -1;
    private int getPostItemResponseCodeAdmin = -1;
    private int updatePostItemResponseCodeAdmin = -1;
    private RequestPost adminUpdatedPost = null;
    private int deleteUserPostItemResponseCodeAdmin = -1;
    private int deleteOwnUserPostItemResponseCodeAdmin = -1;
    //endregion

    //region User local instances
    private RequestPost requestPostUser = null;
    private int getAllPostResponseCodeUser = -1;
    private Object postRequestRawResponseUser = null;
    private int postRequestResponseCodeUser = -1;
    private int updatePostItemResponseCodeUser = -1;
    private Object userListResponse = null;
    //endregion

    //region User 2 local instances
    private RequestPost requestPostUser2 = null;
    private int deleteUserPostItemResponseCodeRootUser2 = -1;
    private int deleteOwnUserPostItemResponseCodeUser2 = -1;
    private int updatePostItemResponseCodeRootUser2 = -1;
    private Object createdTestUser2RequestResponse = null;
    private Object testUser2LoginResponse = null;
    private static final String TEST_USER2_PASSWORD = "test123";
    private static final String TEST_USER2_EMAIL = "testuser2@test.com";
    //endregion
    //endregion

    //region Scenario 4: Add second test user to database with root user authorization
    @When("^root user create the user2 test object containing the test user data$")
    public void rootUserCreateTheUserTestObjectContainingTheTestUserData() {
        this.testUser2Object = new NewUserObject(TEST_USER2_EMAIL,"Test","User 2","Occupation 2",TEST_USER2_PASSWORD);

       //(String email, String firstName, String lastName, String occupation, String password)
    }

    @Then("^root user send POST request to /user/create with 'testuser2@test.com'$")
    public void rootUserSendPOSTRequestToUserCreateWithTestUser2_TestCom() {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(JSONKeys.EMAIL,this.testUser2Object.getEmail());
        requestBody.put(JSONKeys.FIRST_NAME,this.testUser2Object.getFirstName());
        requestBody.put(JSONKeys.LAST_NAME,this.testUser2Object.getLastName());
        requestBody.put(JSONKeys.OCCUPATION,this.testUser2Object.getOccupation());
        requestBody.put(JSONKeys.PASSWORD,this.testUser2Object.getPassword());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.CREATE_USER.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        createdTestUser2RequestResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed creating user with response code %s and error: %s",responseCode,error));
                    }
                })
        );
    }

    @Then("^successfully created the users 'testuser2@test.com'$")
    public void successfullyCreatedTheUsersTestuserTestCom(){
        StateHolder.created_user2 = JsonParser.toAuthenticatedUser(this.createdTestUser2RequestResponse.toString());
        assumeTrue(!StateHolder.created_user2.getEmail().equals(""));
    }

    @Then("^obtain the test user2 record ID$")
    public void obtainTheTestUserRecordID() {
        assumeTrue(StateHolder.created_user2.getID() != -1);
    }
    //endregion

    //region Scenario 6: Test user_2 authenticate by login with the test user credentials
    @Given("^the test user_2 credentials$")
    public void theTestUser_Credentials()
    {
        assumeTrue(!StateHolder.created_user2.getEmail().equals(""));
    }

    @When("^try to login with the given test user_2 credentials$")
    public void tryToLoginWithTheGivenTestUser_Credentials() {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.EMAIL_KEY,StateHolder.created_user2.getEmail());
        requestBody.put(HttpConstant.PASSWORD_KEY,TEST_USER2_PASSWORD);

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.LOGIN.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        testUser2LoginResponse = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        System.out.println(String.format("Failed loging in with response code: %s and error: %s",responseCode,error));
                    }
                })
        );
    }

    @Then("^successfully login as a user_2$")
    public void successfullyLoginAsAUser_() {
        StateHolder.created_user2 = JsonParser.toAuthenticatedUser(this.testUser2LoginResponse.toString());
        assumeTrue(StateHolder.created_user2.getID() != -1);
    }

    @Then("^obtain the test user_2 authorization token$")
    public void obtainTheTestUser_AuthorizationToken() {
       assumeTrue(!StateHolder.created_user2.getSessionToken().equals(""));
    }
    //endregion

    //region Scenario 7: Root user create a new post item
    @Given("^root user authorization token$")
    public void rootUserAuthorizationToken() {
        assumeTrue(!StateHolder.root_user_authorization_token.equals(""));
    }

    @When("^root user create a new post item instance$")
    public void rootUserCreateANewPostItemInstance() {
        requestPostRootUser = new RequestPost("EDUCATIONAL","This is the root user test post item","Root user test post");
    }

    @When("^root user try calling the /post POST API$")
    public void rootUserTryCallingThePostPOSTAPI() {
        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY,requestPostRootUser.getCategory());
        requestBody.put(HttpConstant.POST_TITLE,requestPostRootUser.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION,requestPostRootUser.getDescription());

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.NEW_POST.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        postRequestResponseCodeRootUser = responseCode;
                        postRequestRawResponseRootUser = response;
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        postRequestResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root user successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void rootUserSuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int expected_response_code) {
        assumeTrue(postRequestResponseCodeRootUser == expected_response_code);
        StateHolder.created_post_root_user = JsonParser.toPost(this.postRequestRawResponseRootUser.toString());
        assumeTrue(StateHolder.created_post_root_user.getID() != -1);
    }
    //endregion

    //region Scenario 8: Admin create a new post item
    @Given("^admin authorization token$")
    public void adminAuthorizationToken() {
        assumeTrue(!StateHolder.created_admin.getSessionToken().equals(""));
    }
    
    @When("^admin create a new post item instance$")
    public void adminCreateANewPostItemInstance() {
        requestPostAdmin = new RequestPost("EDUCATIONAL","This is the admin test post item","Admin test post");
    }

    @When("^admin try calling the /post POST API$")
    public void adminTryCallingThePostPOSTAPI() {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY,requestPostAdmin.getCategory());
        requestBody.put(HttpConstant.POST_TITLE,requestPostAdmin.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION,requestPostAdmin.getDescription());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.NEW_POST.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        postRequestResponseCodeAdmin = responseCode;
                        postRequestRawResponseAdmin = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        postRequestResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^admin successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void adminSuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int expected_response_code)
    {
        assumeTrue(postRequestResponseCodeAdmin == expected_response_code);
        StateHolder.created_post_admin = JsonParser.toPost(this.postRequestRawResponseAdmin.toString());
        assumeTrue(StateHolder.created_post_admin.getID() != -1);
    }
    //endregion

    //region Scenario 9: User create a new post item
    @Given("^user authorization token$")
    public void userAuthorizationToken() {
        assumeTrue(!StateHolder.created_user.getSessionToken().equals(""));
    }
    
    @When("^user create a new post item instance$")
    public void userCreateANewPostItemInstance() {
        requestPostUser = new RequestPost("EDUCATIONAL","This is the user test post item","User test post");
    }

    @When("^user try calling the /post POST API$")
    public void userTryCallingThePostPOSTAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY,requestPostUser.getCategory());
        requestBody.put(HttpConstant.POST_TITLE,requestPostUser.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION,requestPostUser.getDescription());


        Api.call(new ApiCall()
                .setEndPoint(EndPoint.NEW_POST.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        postRequestResponseCodeUser = responseCode;
                        postRequestRawResponseUser = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        postRequestResponseCodeUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void userSuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int expected_response_code)
    {
        assumeTrue(postRequestResponseCodeUser == expected_response_code);
        StateHolder.created_post_user = JsonParser.toPost(this.postRequestRawResponseUser.toString());
        assumeTrue(StateHolder.created_post_user.getID() != -1);
    }
    //endregion

    //region Scenario 10: User 2 create a new post item
    @Given("^user_2 authorization token$")
    public void user_AuthorizationToken() {
        assumeTrue(!StateHolder.created_user2.getSessionToken().equals(""));
    }

    @When("^user_2 create a new post item instance$")
    public void user_CreateANewPostItemInstance() {
        requestPostUser2 = new RequestPost("EDUCATIONAL","This is the user 2 test post item","User 2 test post");
    }

    @When("^user_2 try calling the /post POST API$")
    public void user_TryCallingThePostPOSTAPI() {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user2.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY,requestPostUser2.getCategory());
        requestBody.put(HttpConstant.POST_TITLE,requestPostUser2.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION,requestPostUser2.getDescription());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.NEW_POST.getValue())
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        postRequestResponseCodeUser = responseCode;
                        postRequestRawResponseUser = response;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        postRequestResponseCodeUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user_2 successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void user_SuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int expected_response_code)
    {
        assumeTrue(postRequestResponseCodeUser == expected_response_code);
        StateHolder.created_post_user_2 = JsonParser.toPost(this.postRequestRawResponseUser.toString());
        assumeTrue(StateHolder.created_post_user_2.getID() != -1);
    }
    //endregion

    //region Scenario 11: User retrieve list of post items
    @When("^user try calling the /post/all GET API$")
    public void userTryCallingThePostAllGETAPI() {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_POST.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        getAllPostResponseCodeUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        getAllPostResponseCodeUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user successfully retrieved the list of post items by verifying if the server returns response code (\\d+)$")
    public void userSuccessfullyRetrievedTheListOfPostItemsByVerifyingIfTheServerReturnsResponseCode(int expected_response_code)
    {
       assumeTrue(getAllPostResponseCodeUser == expected_response_code);
    }
    //endregion

    //region Scenario 12: Admin retrieve list of post items
    @When("^admin try calling the /post/all GET API$")
    public void adminTryCallingThePostAllGETAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_POST.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        getAllPostResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        getAllPostResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^admin successfully retrieved the list of post items by verifying if the server returns response code (\\d+)$")
    public void adminSuccessfullyRetrievedTheListOfPostItemsByVerifyingIfTheServerReturnsResponseCode(int expected_response_code) {
       assumeTrue(getAllPostResponseCodeAdmin == expected_response_code);
    }
    //endregion

    //region Scenario 13: Root user retrieve list of items
    @Given("^root authorization token$")
    public void rootAuthorizationToken() {
        assumeTrue(!StateHolder.root_user_authorization_token.equals(""));
    }

    @When("^root try calling the /post/all GET API$")
    public void rootTryCallingThePostAllGETAPI() {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.ALL_POST.getValue())
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        getAllPostResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        getAllPostResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root successfully retrieved the list of post items by verifying if the server returns response code (\\d+)$")
    public void rootSuccessfullyRetrievedTheListOfPostItemsByVerifyingIfTheServerReturnsResponseCode(int expected_response_code) {
        assumeTrue(getAllPostResponseCodeRootUser == expected_response_code);
    }
    //endregion

    //region Scenario 14: Admin retrieve user post item
    @Given("^admin authorization token and user post item ID$")
    public void adminAuthorizationTokenAndUserPostItemIDPostID() {
        assumeTrue(!StateHolder.created_admin.getSessionToken().equals(""));
        assumeTrue(StateHolder.created_post_admin.getID() != -1);
    }

    @When("^admin try calling the /post/id GET API with the post item ID$")
    public void adminTryCallingThePostIdGETAPIWithThePostItemID() {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_admin.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.GET_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        getPostItemResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        getPostItemResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^admin successfully retrieved user post item by verifying if the server returns response code (\\d+)$")
    public void adminSuccessfullyRetrievedUserPostItemByVerifyingIfTheServerReturnsResponseCode(int expected_response_code){
        assumeTrue(getPostItemResponseCodeAdmin == expected_response_code);
    }
    //endregion

    //region Scenario 15: root user retrieve user post item
    @Given("^root authorization token and user post item ID$")
    public void rootAuthorizationTokenAndUserPostItemIDPostID() {
        assumeTrue(!StateHolder.root_user_authorization_token.equals(""));
        assumeTrue(StateHolder.created_post_root_user.getID() != -1);
    }

    @When("^root try calling the /post/id GET API with the post item ID$")
    public void rootTryCallingThePostIdGETAPIWithThePostItemID()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.GET_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        getPostItemResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        getPostItemResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root successfully retrieved user post item by verifying if the server returns response code (\\d+)$")
    public void rootSuccessfullyRetrievedUserPostItemByVerifyingIfTheServerReturnsResponseCode(int expected_response_code) {
        assumeTrue(getPostItemResponseCodeRootUser == expected_response_code);
    }
    //endregion

    //region Scenario 17: admin attempt to update the user post item
    @Given("^admin authorization token and post item ID$")
    public void adminAuthorizationTokenAndPostItemID()
    {
        assumeTrue(!StateHolder.created_admin.getSessionToken().equals(""));
        assumeTrue(StateHolder.created_post_user.getID() != -1);
    }

    @When("^admin create an update post item instance$")
    public void adminCreateAnUpdatePostItemInstance()
    {
        adminUpdatedPost = new RequestPost("EDUCATIONAL","This is a updated post item","Updated admin title");
        assumeTrue(adminUpdatedPost != null);
    }

    @When("^admin try calling the /post/update/id PUT API$")
    public void adminTryCallingThePostUpdateIdPUTAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY,adminUpdatedPost.getCategory());
        requestBody.put(HttpConstant.POST_TITLE,adminUpdatedPost.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION,adminUpdatedPost.getDescription());


        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.UPDATE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        updatePostItemResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        updatePostItemResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^admin got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR$")
    public void adminGotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATOR(int expected_response_code) {
        assumeTrue(updatePostItemResponseCodeAdmin == expected_response_code);
    }
    //endregion

    //region Scenario 18: Root user attempt to update the user post item
    @Given("^root user authorization token and post item ID$")
    public void rootUserAuthorizationTokenAndPostItemID() {
        assumeTrue(!StateHolder.root_user_authorization_token.equals(""));
        assumeTrue(StateHolder.created_post_user.getID() != -1);
    }

    @When("^root user create an update post item instance$")
    public void rootUserCreateAnUpdatePostItemInstance()
    {
        rootUpdatedPost = new RequestPost(StateHolder.created_post_user.getCategory(),"Root user updated post","Root user update");
    }

    @When("^root user try calling the /post/update/id PUT API$")
    public void rootUserTryCallingThePostUpdateIdPUTAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY, rootUpdatedPost.getCategory());
        requestBody.put(HttpConstant.POST_TITLE, rootUpdatedPost.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION, rootUpdatedPost.getDescription());


        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.UPDATE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        updatePostItemResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        updatePostItemResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root user got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR$")
    public void rootUserGotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATOR(int expected_response_code)
    {
        assumeTrue(updatePostItemResponseCodeRootUser == expected_response_code);
    }
    //endregion

    //region Scenario 19: user 2 attempt to update the user post item
    @Given("^user_2 authorization token and post item ID$")
    public void user_AuthorizationTokenAndPostItemID() {
        assumeTrue(!StateHolder.created_user2.getSessionToken().equals(""));
        assumeTrue(StateHolder.created_post_user.getID() != -1);
    }


    @When("^user_2 create an update post item instance$")
    public void user_CreateAnUpdatePostItemInstance() {
        requestPostUser2 = new RequestPost(StateHolder.created_post_user.getCategory(),"Root user updated post","Root user update");
        assumeTrue(requestPostUser2 != null);
    }

    @When("^user_2 try calling the /post/update/id PUT API$")
    public void user_TryCallingThePostUpdateIdPUTAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user2.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY, requestPostUser2.getCategory());
        requestBody.put(HttpConstant.POST_TITLE, requestPostUser2.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION, requestPostUser2.getDescription());


        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.UPDATE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        updatePostItemResponseCodeRootUser2 = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        updatePostItemResponseCodeRootUser2 = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user_2 got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR$")
    public void user_GotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATOR(int expected_response_code)
    {
        assumeTrue(updatePostItemResponseCodeRootUser2 == expected_response_code);
    }
    //endregion

    //region Scenario 20: user (post creator) attempt to update the post item
    @Given("^user authorization token and post item ID$")
    public void userAuthorizationTokenAndPostItemID()
    {
        assumeTrue(!StateHolder.created_user.getSessionToken().equals(""));
        assumeTrue(StateHolder.created_post_user.getID() != -1);
    }

    @When("^user create an update post item instance$")
    public void userCreateAnUpdatePostItemInstance()
    {
        assumeTrue(StateHolder.created_post_user_2 != null);
    }

    @When("^user try calling the /post/update/id PUT API$")
    public void userTryCallingThePostUpdateIdPUTAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        HashMap<String,String> requestBody = new HashMap<>();
        requestBody.put(HttpConstant.POST_CATEGORY, StateHolder.created_post_user_2.getCategory());
        requestBody.put(HttpConstant.POST_TITLE, StateHolder.created_post_user_2.getTitle());
        requestBody.put(HttpConstant.POST_DESCRIPTION, StateHolder.created_post_user_2.getDescription());

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.UPDATE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setBody(requestBody)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        updatePostItemResponseCodeUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        updatePostItemResponseCodeUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user successfully updated post item data since server returns response code (\\d+)$")
    public void userSuccessfullyUpdatedPostItemDataSinceServerReturnsResponseCode(int expected_response_code)
    {
        assumeTrue(updatePostItemResponseCodeUser == expected_response_code);
    }
    //endregion

    //region Scenario 21: Root user attempt to remove post item
    @When("^root user try calling the /post/id DELETE API$")
    public void rootUserTryCallingThePostIdDELETEAPI()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_root_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        deletePostItemResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deletePostItemResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root successfully removed post item data since server returns response code (\\d+)$")
    public void rootSuccessfullyRemovedPostItemData(int expected_response_code)
    {
        assumeTrue(deletePostItemResponseCodeRootUser == expected_response_code);
    }
    //endregion

    //region Scenario 22: user 2 attempt to remove the user post item
    @When("^user_2 try calling the /post/id DELETE API$")
    public void user_TryCallingThePostIdDELETEAPI() {
        assumeTrue(StateHolder.created_post_user != null);

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user2.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        deleteUserPostItemResponseCodeRootUser2 = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deleteUserPostItemResponseCodeRootUser2 = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user_2 got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR, ADMIN OR ROOT USER$")
    public void user_GotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATORADMINORROOTUSER(int expected_response_code)
    {
        assumeTrue(deleteUserPostItemResponseCodeRootUser2 == expected_response_code);
    }
    //endregion

    //region Scenario 23: admin attempt to remove the user post item
    @Given("^admin authorization token and post item ID to delete$")
    public void adminAuthorizationTokenAndPostItemIDToDelete() {
        assumeTrue(!StateHolder.created_admin.getSessionToken().equals(""));
    }

    @When("^admin try calling the /post/id DELETE API$")
    public void adminTryCallingThePostIdDELETEAPI() {
        assumeTrue(StateHolder.created_post_user != null);

        if (StateHolder.created_post_user != null)
        {
            HashMap<String,String> requestHeader = new HashMap<>();
            requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
            requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

            String[] parameter = new String[] {
                    String.valueOf(StateHolder.created_post_user.getID())
            };

            Api.call(new ApiCall()
                    .setEndPoint(EndPoint.DELETE_POST.getValue(), parameter)
                    .setHeader(requestHeader)
                    .setCallback(new HttpResponseCallback() {
                        @Override
                        public void OnResponse(int responseCode, Object response) {
                            deleteUserPostItemResponseCodeAdmin = responseCode;
                            assumeTrue(true);
                        }

                        @Override
                        public void OnRequestFailed(int responseCode, String error) {
                            deleteUserPostItemResponseCodeAdmin = responseCode;
                            assumeTrue(true);
                        }
                    })
            );
        }

    }

    @Then("^admin successfully removed post item data since server returns response code (\\d+)$")
    public void adminSuccessfullyRemovedPostItemBasedOnServerResponseCode(int expected_response_code)
    {
        assumeTrue(deleteUserPostItemResponseCodeAdmin == expected_response_code);
    }
    //endregion

    //region Scenario 24: user_2 (post creator) attempt to remove its own post item
    @When("^user_2 try calling the /post/id DELETE API to delete its own post item$")
    public void user_TryCallingThePostIdDELETEAPIToDeleteItsOwnPostItem()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_user2.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_user_2.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        deleteOwnUserPostItemResponseCodeUser2 = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deleteOwnUserPostItemResponseCodeUser2 = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^user_2 successfully updated post item data since server returns response code (\\d+)$")
    public void user_SuccessfullyUpdatedPostItemDataSinceServerReturnsResponseCode(int expected_response_code)
    {
        assumeTrue(deleteOwnUserPostItemResponseCodeUser2 == expected_response_code);
    }
    //endregion

    //region Scenario 25: admin attempt to remove its own post item
    @When("^admin try calling the /post/id DELETE API to remove his own post item$")
    public void adminTryCallingThePostIdDELETEAPIToRemoveHisOwnPostItem()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.created_admin.getSessionToken());
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] parameter = new String[] {
                String.valueOf(StateHolder.created_post_admin.getID())
        };

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_POST.getValue(), parameter)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        deleteOwnUserPostItemResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deleteOwnUserPostItemResponseCodeAdmin = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^admin successfully removed his own post item data since server returns response code (\\d+)$")
    public void adminSuccessfullyRemovedHisOwnPostItemDataSinceServerReturnsResponseCode(int expected_response_code)
    {
        assumeTrue(deleteOwnUserPostItemResponseCodeAdmin == expected_response_code);
    }
    //endregion

    //region Scenario 27: root user remove test user_2 from the database
    @Given("^the root user authorization token and test user_2 record ID$")
    public void theRootUserAuthorizationTokenAndTestUser_RecordID()
    {
        assumeTrue(!StateHolder.root_user_authorization_token.equals(""));
        assumeTrue(StateHolder.created_user2.getID() != -1 );
    }

    @When("^root user send a DELETE request to /user/ including the authorization token and test user_2 record ID$")
    public void rootUserSendADELETERequestToUserIncludingTheAuthorizationTokenAndTestUser_RecordID()
    {

        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);
        requestHeader.put(HttpConstant.CONTENT_TYPE_KEY,HttpConstant.MEDIA_TYPE_JSON);

        String[] params = new String[] {Integer.toString(StateHolder.created_user2.getID())};

        Api.call(new ApiCall()
                .setEndPoint(EndPoint.DELETE_USER.getValue(),params)
                .setHeader(requestHeader)
                .setCallback(new HttpResponseCallback() {
                    @Override
                    public void OnResponse(int responseCode, Object response) {
                        deleteTestUser2ResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }

                    @Override
                    public void OnRequestFailed(int responseCode, String error) {
                        deleteTestUser2ResponseCodeRootUser = responseCode;
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root user get a response that test user_2 has been successfully removed$")
    public void rootUserGetAResponseThatTestUser_HasBeenSuccessfullyRemoved()
    {
        assumeTrue(deleteTestUser2ResponseCodeRootUser == 200);
    }

    @Then("^root user verified if the test user_2 is indeed removed from the database by calling /user/all$")
    public void rootUserVerifiedIfTheTestUser_IsIndeedRemovedFromTheDatabaseByCallingUserAll()
    {
        HashMap<String,String> requestHeader = new HashMap<>();
        requestHeader.put(HttpConstant.AUTHORIZATION_HEADER_KEY,StateHolder.root_user_authorization_token);

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
                        assumeTrue(true);
                    }
                })
        );
    }

    @Then("^root user verified if test user_2 has been successfully removed from the database$")
    public void rootUserVerifiedIfTestUser_HasBeenSuccessfullyRemovedFromTheDatabase()
    {
        List<AuthenticatedUser> user_list = JsonParser.toUserList(this.userListResponse.toString());

        if (user_list.size() > 0)
        {
            boolean user_found = user_list.stream().allMatch(user -> StateHolder.created_user2.getID() == user.getID());
            assumeTrue(!user_found);
        }

        assumeTrue(true);
    }
    //endregion
}
