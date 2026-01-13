@admin-management
Feature: Admin Authentication and Session Management
  As an Admin, I want to manage my login, password settings, and logout sessions through the API.

  Background:
    Given Admin sets No Auth

  # --- SECTION 1: LOGIN & TOKEN GENERATION ---
  # @admin-auth-module

  @Login-Success
  Scenario: Check if admin able to generate token with valid credential
    Given Admin creates request with valid credentials
    When Admin calls Post Https method with valid endpoint
    Then Admin receives 200 created with auto generated token

  @Login-Invalid
  Scenario Outline: Check admin login with various invalid inputs or protocols
    Given Admin creates request with <condition>
    When Admin calls <method> with <endpoint_type>
    Then Admin receives <status_code> for Login

    Examples:
      | condition                         | method            | endpoint_type        | status_code |
      | special characters in admin email | Post Https method | valid endpoint       | 400         |
      | special characters in password    | Post Https method | valid endpoint       | 401         |
      | numbers in email                  | Post Https method | valid endpoint       | 400         |
      | numbers in password               | Post Https method | valid endpoint       | 400         |
      | Null password                     | Post Https method | valid endpoint       | 400         |
      | Null email                        | Post Https method | valid endpoint       | 400         |
      | Null body                         | Post Https method | valid endpoint       | 400         |
      | valid credentials                 | GET HTTPS method  | post endpoint        | 405         |
      | valid credentials                 | Post Https method | invalid content type | 415         |

  # --- SECTION 2: FORGOT PASSWORD ---

  @Forgot-Password-Success
  Scenario: Check if admin able to generate token with valid credential for forgot-password
    Given Admin creates request for forgot-password with valid credentials
    When Admin calls Post Https method for forgot-password with valid endpoint
    Then Admin receives 201 created with auto generated token for forgot-password

  @Forgot-Password-Invalid
  Scenario Outline: Check forgot-password with invalid inputs or metadata
    Given Admin creates request for forgot-password with <condition>
    When Admin calls Post Https method for forgot-password with <endpoint_type>
    Then Admin receives <status_code> for forgot-password

    Examples:
      | condition                         | endpoint_type        | status_code |
      | special characters in admin email | valid endpoint       | 400         |
      | invalid admin email               | valid endpoint       | 400         |
      | Null body                         | valid endpoint       | 400         |
      | valid credentials                 | invalid content type | 415         |
      | valid credentials                 | invalid endpoint     | 404         |

  # --- SECTION 3: LOGOUT ---
  # @admin-logout-module

  @Logout-Scenarios
  Scenario Outline: Check admin logout with various inputs or protocols
    Given Admin sets authorization to bearer Token with token
    And Admin creates request for logout
    When Admin calls "<method>" method with "<endpoint_type>" for logout
    Then Admin receives <status_code> for logout

    Examples:
      | method | endpoint_type    | status_code |
      | GET    | valid endpoint   | 200         |
      | GET    | invalid endpoint | 404         |
      | GET    | invalid base URL | 404         |
      | POST   | valid endpoint   | 405         |

  @Logout-Unauthorized
  Scenario: Check logout behavior with No Auth
    Given Admin sets No Auth for logout
    And Admin creates request for logout
    When Admin calls "GET" method with "valid endpoint" for logout
    Then Admin receives 401 for logout

  @Logout-ExpiredToken
  Scenario: Check logout behavior with expired or old token
    Given Admin sets authorization to bearer Token with old token
    And Admin creates request for logout
    When Admin calls "GET" method with "valid endpoint" for logout
    Then Admin receives 401 for logout