Feature: GetUsersById Test Scenarios

  Scenario: Success Get Single User Control
    Given Prepare Url "https://reqres.in/api/users/2"
    When Send Request "GET"
    Then Expected to see 200 status code
    Then Response Control
      | path    | value |
      | data.id | 2     |