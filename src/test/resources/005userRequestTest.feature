Feature: Perform all user tasks and also perform forbidden action and determine if the server block user from performing such tasks
  Scenario: root user authenticate by login with the root user credentials
    Given the root user credentials
    When try to login with the given root user credentials
    Then successfully login as the root user
    Then obtain the authorization token

  Scenario: Add test user to database with root user authorization
    Given the root user authorization token
    When root user create the test object containing the test user data
    Then root user send POST request to /user/create with 'testUser@test.com'
    Then successfully created the user 'testUser@test.com'
    Then obtain the test user record ID

  Scenario: test user authenticate by login with the test user credentials
    Given the test user credentials
    When try to login with the given test user credentials
    Then successfully login as a user
    Then obtain the test user authorization token

  Scenario: test user update his personal information
    Given the test user authorization token
    When creating updated test user object
    Then test user send a PUT request to /user/update/ including the updated personal data
    Then successfully updated personal data by verify if the server return a response code 200

  Scenario: test user attempt to retrieve list of admins and the server successfully block test user from performing such task
    When test user send a GET request to /admin/all
    Then the server successfully block test user from retrieving list of admins by responding with response code 401 'unauthorized'

  Scenario: test user attempt to retrieve list of users and the server successfully block test user from performing such task
    When test user send a GET request to /user/all
    Then the server successfully block test user from retrieving list of users by responding with response code 401 'unauthorized'

  Scenario: test user attempt to create a new admin and the server successfully block test user from performing such task
    When test user create a test admin user instance
    Then test user send a POST request to /admin/create
    Then the server successfully block test user from creating new admin by responding with response code 401 'unauthorized'

  Scenario: root user remove test user from the database
    Given the root user authorization token and test user record ID
    When root user send a DELETE request to /user/ including the authorization token and test user record ID
    Then root user get a response that test user has been successfully removed
    Then root user verified if the test user is indeed removed from the database by calling /user/all
    Then root user verified if test user has been successfully removed from the database