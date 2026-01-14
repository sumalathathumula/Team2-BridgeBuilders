@reset-password-module
Feature: Admin Reset Password Controller
  As an Admin, I want to manage password resets through the API.
#
#  @Reset-Success
#  Scenario: Check if admin able to resetPassword
#    Given Admin sets "valid" authorization for reset password
#    And Admin creates reset password request with "valid" email and "new" password
#    When Admin calls "POST" method with "valid" endpoint for reset password
#    Then Admin receives 200 for reset password

  @Reset-Negative
  Scenario Outline: Check reset password with various invalid inputs or metadata
    Given Admin sets "<authorization_type>" authorization for reset password
    And Admin creates reset password request with "<email_state>" email and "<password_state>" password
    When Admin calls "<method>" method with "<endpoint_type>" endpoint for reset password
    Then Admin receives <status_code> for reset password

    Examples:
      | authorization_type | email_state | password_state | method | endpoint_type | status_code |
      | valid              | valid       | valid          | POST   | valid         | 200         |
#      | no                 | valid       | old            | POST   | valid         | 401         |
#      | valid              | new         | special_char   | POST   | valid         | 400         |
#      | valid              | valid       | new            | POST   | invalid       | 404         |
#      | valid              | valid       | new            | GET    | valid         | 405         |
#      | valid              | new         | invalid        | POST   | valid         | 400         |
#      | authorization      | valid       | new            | POST   | invalid baseURL | 404         |
#      | authorization      | valid       | invalid content type | POST   | valid endpoint   | 415         |