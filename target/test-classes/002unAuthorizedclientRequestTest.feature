Feature: Block request when an API requires authorized
  Scenario: Client try to make a request to an API which requires authorization token
    When the client calls api without an authorization header
    Then the client receives http status 400 'bad request' response


