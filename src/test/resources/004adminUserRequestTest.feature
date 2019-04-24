Feature: Perform all admin tasks and also perform forbidden action and determine if the server block such tasks
  Scenario: root user authenticate by login with the root user credentials
    Given the root user credentials
    When try to login with the given root user credentials
    Then successfully login as the root user
    Then obtain the authorization token

  Scenario: Add test admin to database with root user authorization
    Given the root user authorization token
    When root user create the test object containing the test admin data
    Then root user send POST request to /admin/create with 'adminTest@test.com'
    Then successfully created the admin user 'adminTest@test.com'
    Then obtain the test admin record ID

  Scenario: Test admin authenticate by login with test admin credentials
    Given test admin login credentials
    Then test admin send POST request to /auth/login with test admin login credentials
    Then successfully logged as test admin
    Then obtain test admin authorization token
    Then obtain test admin user record ID

  Scenario: Test admin create test user and add it to database
    When test admin create test user object
    Then test admin send POST request to /user/create including test user data
    Then successfully created test user
    Then obtain test user record ID

  Scenario: Test admin retrieve list of all users
    When test admin send GET request to /user/all to retrieve list of all users
    Then successfully retrieved list of users

  Scenario: Test admin attempt to retrieve list of all admins and the server block such request since admin are not authorization to perform such task
    When test admin send GET request to /admin/all to retrieve list of all admins
    Then server successfully block such attempt by sending response code 401 'unauthorized'

  Scenario: Test admin attempt to create a new admin and the server block such request since admins are not authorization to perform such task
    When test admin create new admin user object
    When test admin send POST request to /admin/create including the new test admin user object
    Then server successfully block such attempt to create new admin by sending response code 401 'unauthorized'

  Scenario: Test admin attempt to update personal information of test user and the server block such request since only the account owner is allowed to do such of this nature
    When test admin created updated object of the test user
    When test admin send PUT request to /user/update including the test user record ID and updated values
    Then server successfully block test user update task since by sending response code 400 'unauthorized'

  Scenario: Test admin attempt to remove test admin from the database by using the test admin authorization token and the server block such request since only the account owner is allowed to perform task of this nature
    When test admin send DELETE request to /admin/ including the test user record ID
    Then server successfully block delete admin task by sending response code 401 'unauthorized'

  Scenario: Test admin remove test user from the database by using the test admin authorization token
    When test admin send DELETE request to /user/ including the test user record ID
    Then test admin successfully deleted test user from database by checking respond code equal to 200
    Then determine if test admin successfully deleted test user from database

  Scenario: root user remove test admin from the database
    Given the root user authorization token and test admin user record ID
    When root user send a DELETE request to /admin/ including the authorization token and test admin record ID
    Then root user get a response that admin has been successfully removed
    Then root user verified if the test admin is indeed removed from the database by calling /admin/all
    Then test admin has been successfully removed from the database

