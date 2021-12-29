# restapi_BDD_examples

It is a sample structure created for Integration tests in WebServices. It is a structure managed in BDD style and integrated with JSON files.
The biggest advantage is that tests can be written with only feature and json files without coding.

### Using

- JAVA
- RestAssured
- Cucumber
- TestNG
- Jayway(Json-Path)
- Gson

## How to use

To run all tests, the following command is written on the terminal screen.

`$ mvn test`

### Sample Feature File

```
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
    When Send Request "POST"
    Then Expected to see 201 status code
    Then Expected to see not null control
      | id |
    Then Response Control
      | path | value  |
      | name | deneme |
      | job  | avare  |
```

### Sample Json File

```
{
  "name": "morpheus",
  "job": "leader"
}
```

