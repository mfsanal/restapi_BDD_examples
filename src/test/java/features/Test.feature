Feature: Test Scenarios

  Scenario: Test01
    Given Prepare Url "https://reqres.in/api/users"
    Given Prepare Headers
      | key  | value |
      | abc  | 123   |
      | abcd | 1234  |
    When Send Request "GET"