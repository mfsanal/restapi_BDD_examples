Feature: PostRegister Scenarios

  Scenario: Success Register Control
    Given Prepare Url "https://reqres.in/api/register"
    Given Prepare Request Body "Register"
      | key      | value              |
      | email    | eve.holt@reqres.in |
      | password | pistol             |
    Given Prepare Headers
      | key          | value            |
      | Content-Type | application/json |
      | Accept       | */*              |
    When Send Request "POST"
    Then Expected to see 200 status code
    Then Expected to see not null control
      | id    |
      | token |


