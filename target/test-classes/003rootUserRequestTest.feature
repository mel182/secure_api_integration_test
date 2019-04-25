Feature: Perform all root user task actions on created admins and users
  Scenario: root user authenticate by login with the root user credentials
    Given the root user credentials
    When try to login with the given root user credentials
    Then successfully login as the root user
    Then obtain the authorization token

  Scenario: Add test admin to database with root user authorization
    Given the root user authorization token
    When root user create the test object containing the test admin data
    Then root user send POST request to /admin/create including the test user data
    Then successfully created the admin user 'adminTest@test.com'
    Then obtain the test admin record ID

  Scenario: root user retrieve the list of all admins and verified if the test admin user is indeed stored on to the database
    Given the root user authorization token, test admin email 'adminTest@test.com' and record ID
    When root user send a GET request to /admin/all with the authorization token
    Then root user successfully retrieve the list of admin users
    Then root user verified if the corresponding test admin is in the response list

  Scenario: root user remove test admin from the database
    Given the root user authorization token and test admin user record ID
    When root user send a DELETE request to /admin/ including the authorization token and test admin record ID
    Then root user get a response that admin has been successfully removed
    Then root user verified if the test admin is indeed removed from the database by calling /admin/all
    Then test admin has been successfully removed from the database

  Scenario: Add test user to database with root user authorization
    Given the root user authorization token
    When root user create the test object containing the test user data
    Then root user send POST request to /user/create with 'testUser@test.com'
    Then successfully created the user 'testUser@test.com'
    Then obtain the test user record ID

  Scenario: root user retrieve the list of all users and verified if the test user is indeed stored on to the database
    Given the root user authorization token and test user record ID
    When root user send a GET request to /users/all including the authorization token
    Then root user successfully retrieve the list of users
    Then root user verified if the corresponding test user is in the response list

  Scenario: root user remove test user from the database
    Given the root user authorization token and test user record ID
    When root user send a DELETE request to /user/ including the authorization token and test user record ID
    Then root user get a response that test user has been successfully removed
    Then root user verified if the test user is indeed removed from the database by calling /user/all
    Then root user verified if test user has been successfully removed from the database
