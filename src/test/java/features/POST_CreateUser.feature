Feature: Test Scenarios

  Scenario: Test01
    Given Prepare Url "https://reqres.in/api/users"
    Given Prepare Request Body "CreateUser"
      | key     | value  |
      | name    | deneme |
      | job     | avare  |
    Given Prepare Headers
      | key  | value |
      | abc  | 123   |
      | abcd | 1234  |
    When Send Request "POST"