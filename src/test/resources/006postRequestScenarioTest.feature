Feature: Perform all tasks related to the post API and also perform forbidden action and determine if the server block unauthorized actions
  Scenario: 1 root user authenticate by login with the root user credentials
    Given the root user credentials
    When try to login with the given root user credentials
    Then successfully login as the root user
    Then obtain the authorization token

  Scenario: 2 Add test admin to database with root user authorization
    Given the root user authorization token
    When root user create the test object containing the test admin data
    Then root user send POST request to /admin/create including the test user data
    Then successfully created the admin user 'adminTest@test.com'
    Then obtain the test admin record ID

  Scenario: 3 Add test user to database with root user authorization
    When root user create the test object containing the test user data
    Then root user send POST request to /user/create with 'testUser@test.com'
    Then successfully created the user 'testUser@test.com'
    Then obtain the test user record ID

  Scenario: 4 Add second test user to database with root user authorization
    When root user create the user2 test object containing the test user data
    Then root user send POST request to /user/create with 'testuser2@test.com'
    Then successfully created the users 'testuser2@test.com'
    Then obtain the test user2 record ID

  Scenario: 5 Test admin authenticate by login with test admin credentials
    Given test admin login credentials
    Then test admin send POST request to /auth/login with test admin login credentials
    Then successfully logged as test admin
    Then obtain test admin authorization token
    Then obtain test admin user record ID

  Scenario: 5 test user authenticate by login with the test user credentials
    Given the test user credentials
    When try to login with the given test user credentials
    Then successfully login as a user
    Then obtain the test user authorization token

  Scenario: 6 test user_2 authenticate by login with the test user credentials
    Given the test user_2 credentials
    When try to login with the given test user_2 credentials
    Then successfully login as a user_2
    Then obtain the test user_2 authorization token

  Scenario: 7 root user create a new post item
    Given root user authorization token
    When root user create a new post item instance
    When root user try calling the /post POST API
    Then root user successfully create a new post item by verifying if the server returns response code 200

  Scenario: 8 admin create a new post item
    Given admin authorization token
    When admin create a new post item instance
    When admin try calling the /post POST API
    Then admin successfully create a new post item by verifying if the server returns response code 200

  Scenario: 9 user create a new post item
    Given user authorization token
    When user create a new post item instance
    When user try calling the /post POST API
    Then user successfully create a new post item by verifying if the server returns response code 200

  Scenario: 10 user_2 create a new post item
    Given user_2 authorization token
    When user_2 create a new post item instance
    When user_2 try calling the /post POST API
    Then user_2 successfully create a new post item by verifying if the server returns response code 200

  Scenario: 11 user retrieve list of post items
    Given user authorization token
    When user try calling the /post/all GET API
    Then user successfully retrieved the list of post items by verifying if the server returns response code 200

  Scenario: 12 admin retrieve list of post items
    Given admin authorization token
    When admin try calling the /post/all GET API
    Then admin successfully retrieved the list of post items by verifying if the server returns response code 200

  Scenario: 13 root retrieve list of post items
    Given root authorization token
    When root try calling the /post/all GET API
    Then root successfully retrieved the list of post items by verifying if the server returns response code 200

  Scenario: 14 admin retrieve user post item
    Given admin authorization token and user post item ID
    When admin try calling the /post/id GET API with the post item ID
    Then admin successfully retrieved user post item by verifying if the server returns response code 200

  Scenario: 15 root user retrieve user post item
    Given root authorization token and user post item ID
    When root try calling the /post/id GET API with the post item ID
    Then root successfully retrieved user post item by verifying if the server returns response code 200

  Scenario: 16 user retrieve user post item
    Given root authorization token and user post item ID
    When root try calling the /post/id GET API with the post item ID
    Then root successfully retrieved user post item by verifying if the server returns response code 200

  Scenario: 17 admin attempt to update the user post item
    Given admin authorization token and post item ID
    When admin create an update post item instance
    When admin try calling the /post/update/id PUT API
    Then admin got an unauthorized 401 response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR

  Scenario: 18 root user attempt to update the user post item
    Given root user authorization token and post item ID
    When root user create an update post item instance
    When root user try calling the /post/update/id PUT API
    Then root user got an unauthorized 401 response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR

  Scenario: 19 user_2 attempt to update the user post item
    Given user_2 authorization token and post item ID
    When user_2 create an update post item instance
    When user_2 try calling the /post/update/id PUT API
    Then user_2 got an unauthorized 401 response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR

  Scenario: 20 user (post creator) attempt to update the post item
    Given user authorization token and post item ID
    When user create an update post item instance
    When user try calling the /post/update/id PUT API
    Then user successfully updated post item data since server returns response code 200

  Scenario: 21 root user attempt to remove post item
    Given root user authorization token and post item ID
    When root user try calling the /post/id DELETE API
    Then root successfully removed post item data since server returns response code 200

  Scenario: 22 user_2 attempt to remove the user post item
    Given user_2 authorization token and post item ID
    When user_2 try calling the /post/id DELETE API
    Then user_2 got an unauthorized 401 response back from the server since it is not authorized to perform such task ONLY THE POST CREATOR, ADMIN OR ROOT USER

  Scenario: 23 admin attempt to remove the user post item
    Given admin authorization token and post item ID
    When admin try calling the /post/id DELETE API
    Then admin successfully removed post item data since server returns response code 200

  Scenario: 24 user_2 (post creator) attempt to remove its own post item
    Given user_2 authorization token and post item ID
    When user_2 try calling the /post/id DELETE API
    Then user_2 successfully updated post item data since server returns response code 200

  Scenario: 25 admin attempt to remove its own post item
    Given admin authorization token and post item ID
    When admin try calling the /post/id DELETE API
    Then admin successfully updated post item data since server returns response code 200

  Scenario: 26 root user remove test user from the database
    Given the root user authorization token and test user record ID
    When root user send a DELETE request to /user/ including the authorization token and test user record ID
    Then root user get a response that test user has been successfully removed
    Then root user verified if the test user is indeed removed from the database by calling /user/all
    Then root user verified if test user has been successfully removed from the database

  Scenario: 27 root user remove test user_2 from the database
    Given the root user authorization token and test user_2 record ID
    When root user send a DELETE request to /user/ including the authorization token and test user_2 record ID
    Then root user get a response that test user_2 has been successfully removed
    Then root user verified if the test user_2 is indeed removed from the database by calling /user/all
    Then root user verified if test user_2 has been successfully removed from the database

  Scenario: 28 Add test admin to database with root user authorization
    Given the root user authorization token
    When root user create the test object containing the test admin data
    Then root user send POST request to /admin/create including the test user data
    Then successfully created the admin user 'adminTest@test.com'
    Then obtain the test admin record ID

