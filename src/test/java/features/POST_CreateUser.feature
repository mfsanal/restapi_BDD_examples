Feature: Test Scenarios

  Scenario: Success Create User Control
    Given Prepare Url "https://reqres.in/api/users"
    Given Prepare Request Body "CreateUser"
      | key  | value  |
      | name | deneme |
      | job  | avare  |
    Given Prepare Headers
      | key  | value |
      | abc  | 123   |
      | abcd | 1234  |
    When Send Request "POST"
    Then Expected to see 201 status code
    Then Expected to see not null control
      | id |


  Scenario: Success Get Single User Control
    Given Prepare Url "https://reqres.in/api/users/2"
    When Send Request "GET"
    Then Expected to see 200 status code
    Then Response Control
      | path    | value |
      | data.id | 2     |