#@admin-management
@reset-password-module
Feature: Admin Reset Password Controller
  As an Admin, I want to manage password resets through the API.

  @Reset-Negative
  Scenario Outline: Check reset password with various invalid inputs or metadata
    Given Admin sets "<authorization_type>" authorization for reset password
    And Admin creates reset password request with "<email_state>" email and "<password_state>" password
    When Admin calls "<method>" method with "<endpoint_type>" endpoint for reset password
    Then Admin receives <status_code> for reset password

    Examples:
      | authorization_type | email_state | password_state | method | endpoint_type | status_code |
      | no                 | valid       | old            | POST   | valid         | 401         |
      | valid              | new         | special_char   | POST   | valid         | 400         |
      | valid              | valid       | new            | POST   | invalid       | 404         |
      | valid              | valid       | new            | GET    | valid         | 405         |
      | valid              | invalid     | new            | POST   | valid         | 400         |
      | valid              | valid       | valid          | POST   | valid         | 200         |

  @Reset-Negative-BaseURL
  Scenario: Check reset password with invalid base URL
    Given Admin sets "valid" authorization for reset password
    And Admin creates reset password request with "valid" email and "new" password
    When Admin calls Post Https method for reset password with invalid base URI
    Then Admin receives 404 for reset password

  @Reset-Negative-ContentType
  Scenario: Check reset password with invalid content type
    Given Admin sets "valid" authorization for reset password
    And Admin creates reset password request with "valid" email and "new" password
    When Admin calls Post Https method for reset password with invalid content type
    Then Admin receives 415 for reset password

#      | valid              | valid       | new            | POST   | valid         | 200         |   //>> DO NOT ENABLE




