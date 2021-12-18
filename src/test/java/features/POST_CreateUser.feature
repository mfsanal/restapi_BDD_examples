Feature: PostCreateUser Test Scenarios

  Scenario: Success Create User Control
    Given Prepare Url "https://reqres.in/api/users"
    Given Prepare Request Body "CreateUser"
      | key  | value  |
      | name | deneme |
      | job  | avare  |
    Given Prepare Headers
      | key          | value            |
      | Content-Type | application/json |
      | Accept       | */*              |
    When Send Request "POST"
    Then Expected to see 201 status code
    Then Expected to see not null control
      | id |
    Then Response Control
      | path | value  |
      | name | deneme |
      | job  | avare  |

  Scenario: Success Create User Control2
    Given Prepare Url "https://reqres.in/api/users"
    Given Prepare Headers
      | key          | value            |
      | Content-Type | application/json |
      | Accept       | */*              |
    Given Fields Control "name" at "CreateUser"
    When Send Request "POST"
    Then Expected to see 201 status code