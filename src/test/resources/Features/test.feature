@admin-auth-module
Feature: Admin Authentication and Password Management
  As an Admin, I want to manage my session and password settings through the API.

  Background:
    Given Admin sets No Auth

  # --- SECTION 1: LOGIN & TOKEN GENERATION ---

  Scenario: Check if admin able to generate token with valid credential
    Given Admin creates request with valid credentials
    When Admin calls Post Https method with valid endpoint
    Then Admin receives 200 created with auto generated token

  Scenario Outline: Check admin login with various invalid inputs or protocols
    Given Admin creates request with <condition>
    When Admin calls <method> with <endpoint_type>
    Then Admin receives <status_code>

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

  Scenario: Check if admin able to generate token with valid credential for forgot-password
    Given Admin creates request for forgot-password with valid credentials
    When Admin calls Post Https method for forgot-password with valid endpoint
    Then Admin receives 201 created with auto generated token for forgot-password

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