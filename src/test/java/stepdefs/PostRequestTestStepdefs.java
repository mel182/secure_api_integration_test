package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PostRequestTestStepdefs {

    //region Scenario 4: Add second test user to database with root user authorization
    @When("^root user create the user2 test object containing the test user data$")
    public void rootUserCreateTheUserTestObjectContainingTheTestUserData(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root user send POST request to /user/create with 'testuser2@test.com'$")
    public void rootUserSendPOSTRequestToUserCreateWithTestuser_TestCom(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^successfully created the users 'testuser2@test.com'$")
    public void successfullyCreatedTheUsersTestuserTestCom() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^obtain the test user2 record ID$")
    public void obtainTheTestUserRecordID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 6: Test user_2 authenticate by login with the test user credentials
    @Given("^the test user_2 credentials$")
    public void theTestUser_Credentials() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^try to login with the given test user_2 credentials$")
    public void tryToLoginWithTheGivenTestUser_Credentials() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^successfully login as a user_2$")
    public void successfullyLoginAsAUser_(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^obtain the test user_2 authorization token$")
    public void obtainTheTestUser_AuthorizationToken(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 7: Root user create a new post item
    @Given("^root user authorization token$")
    public void rootUserAuthorizationToken() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^root user create a new post item instance$")
    public void rootUserCreateANewPostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^root user try calling the /post POST API$")
    public void rootUserTryCallingThePostPOSTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^root user successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void rootUserSuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 8: Admin create a new post item
    @Given("^admin authorization token$")
    public void adminAuthorizationToken() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
    @When("^admin create a new post item instance$")
    public void adminCreateANewPostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^admin try calling the /post POST API$")
    public void adminTryCallingThePostPOSTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^admin successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void adminSuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 9: User create a new post item
    @Given("^user authorization token$")
    public void userAuthorizationToken() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
    @When("^user create a new post item instance$")
    public void userCreateANewPostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user try calling the /post POST API$")
    public void userTryCallingThePostPOSTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^user successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void userSuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 10: User 2 create a new post item
    @Given("^user_2 authorization token$")
    public void user_AuthorizationToken(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user_2 create a new post item instance$")
    public void user_CreateANewPostItemInstance(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user_2 try calling the /post POST API$")
    public void user_TryCallingThePostPOSTAPI(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^user_2 successfully create a new post item by verifying if the server returns response code (\\d+)$")
    public void user_SuccessfullyCreateANewPostItemByVerifyingIfTheServerReturnsResponseCode(int arg0, int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 11: User retrieve list of post items
    @When("^user try calling the /post/all GET API$")
    public void userTryCallingThePostAllGETAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^user successfully retrieved the list of post items by verifying if the server returns response code (\\d+)$")
    public void userSuccessfullyRetrievedTheListOfPostItemsByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 12: Admin retrieve list of post items
    @When("^admin try calling the /post/all GET API$")
    public void adminTryCallingThePostAllGETAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^admin successfully retrieved the list of post items by verifying if the server returns response code (\\d+)$")
    public void adminSuccessfullyRetrievedTheListOfPostItemsByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 13: Root user retrieve list of items
    @Given("^root authorization token$")
    public void rootAuthorizationToken() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^root try calling the /post/all GET API$")
    public void rootTryCallingThePostAllGETAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root successfully retrieved the list of post items by verifying if the server returns response code (\\d+)$")
    public void rootSuccessfullyRetrievedTheListOfPostItemsByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 14: Admin retrieve user post item
    @Given("^admin authorization token and user post item ID$")
    public void adminAuthorizationTokenAndUserPostItemIDPostID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^admin try calling the /post/id GET API with the post item ID$")
    public void adminTryCallingThePostIdGETAPIWithThePostItemID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^admin successfully retrieved user post item by verifying if the server returns response code (\\d+)$")
    public void adminSuccessfullyRetrievedUserPostItemByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 15: root user retrieve user post item
    @Given("^root authorization token and user post item ID$")
    public void rootAuthorizationTokenAndUserPostItemIDPostID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^root try calling the /post/id GET API with the post item ID$")
    public void rootTryCallingThePostIdGETAPIWithThePostItemID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root successfully retrieved user post item by verifying if the server returns response code (\\d+)$")
    public void rootSuccessfullyRetrievedUserPostItemByVerifyingIfTheServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 17: admin attempt to update the user post item
    @Given("^admin authorization token and post item ID$")
    public void adminAuthorizationTokenAndPostItemID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^admin create an update post item instance$")
    public void adminCreateAnUpdatePostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^admin try calling the /post/update/id PUT API$")
    public void adminTryCallingThePostUpdateIdPUTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^admin got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR$")
    public void adminGotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATOR(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 18: Root user attempt to update the user post item
    @Given("^root user authorization token and post item ID$")
    public void rootUserAuthorizationTokenAndPostItemID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^root user create an update post item instance$")
    public void rootUserCreateAnUpdatePostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^root user try calling the /post/update/id PUT API$")
    public void rootUserTryCallingThePostUpdateIdPUTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^root user got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR$")
    public void rootUserGotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATOR(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 19: user 2 attempt to update the user post item
    @Given("^user_2 authorization token and post item ID$")
    public void user_AuthorizationTokenAndPostItemID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @When("^user_2 create an update post item instance$")
    public void user_CreateAnUpdatePostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user_2 try calling the /post/update/id PUT API$")
    public void user_TryCallingThePostUpdateIdPUTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^user_2 got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR$")
    public void user_GotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATOR(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 20: user (post creator) attempt to update the post item
    @Given("^user authorization token and post item ID$")
    public void userAuthorizationTokenAndPostItemID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user create an update post item instance$")
    public void userCreateAnUpdatePostItemInstance() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^user try calling the /post/update/id PUT API$")
    public void userTryCallingThePostUpdateIdPUTAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^user successfully updated post item data since server returns response code (\\d+)$")
    public void userSuccessfullyUpdatedPostItemDataSinceServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    //endregion

    //region Scenario 21: Root user attempt to remove post item
    @When("^root user try calling the /post/id DELETE API$")
    public void rootUserTryCallingThePostIdDELETEAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root successfully removed post item data since server returns response code (\\d+)$")
    public void rootSuccessfullyRemovedPostItemDataSinceServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 22: user 2 attempt to remove the user post item
    @When("^user_2 try calling the /post/id DELETE API$")
    public void user_TryCallingThePostIdDELETEAPI(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^user_2 got an unauthorized (\\d+) response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR, ADMIN OR ROOT USER$")
    public void user_GotAnUnauthorizedResponseBackFromTheServerSinceItIsNotAuthorizedToPerformSuchTaskONLYTHEPOSTCREATORADMINORROOTUSER(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 23: admin attempt to remove the user post item
    @When("^admin try calling the /post/id DELETE API$")
    public void adminTryCallingThePostIdDELETEAPI() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^admin successfully removed post item data since server returns response code (\\d+)$")
    public void adminSuccessfullyRemovedPostItemDataSinceServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 24: user_2 (post creator) attempt to remove its own post item
    @Then("^user_2 successfully updated post item data since server returns response code (\\d+)$")
    public void user_SuccessfullyUpdatedPostItemDataSinceServerReturnsResponseCode(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 25: admin attempt to remove its own post item
    @Then("^admin successfully updated post item data since server returns response code (\\d+)$")
    public void adminSuccessfullyUpdatedPostItemDataSinceServerReturnsResponseCode(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion

    //region Scenario 27: root user remove test user_2 from the database
    @Given("^the root user authorization token and test user_2 record ID$")
    public void theRootUserAuthorizationTokenAndTestUser_RecordID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^root user send a DELETE request to /user/ including the authorization token and test user_2 record ID$")
    public void rootUserSendADELETERequestToUserIncludingTheAuthorizationTokenAndTestUser_RecordID() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root user get a response that test user_2 has been successfully removed$")
    public void rootUserGetAResponseThatTestUser_HasBeenSuccessfullyRemoved() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root user verified if the test user_2 is indeed removed from the database by calling /user/all$")
    public void rootUserVerifiedIfTheTestUser_IsIndeedRemovedFromTheDatabaseByCallingUserAll() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^root user verified if test user_2 has been successfully removed from the database$")
    public void rootUserVerifiedIfTestUser_HasBeenSuccessfullyRemovedFromTheDatabase(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    //endregion
}
