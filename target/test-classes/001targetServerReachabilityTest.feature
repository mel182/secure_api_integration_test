Feature: Check if the server is running before any requests can be executed
  Scenario: check if the target server is running
    Given the target server base URL
    When the application check if the target server is running
    Then the target server respond positively