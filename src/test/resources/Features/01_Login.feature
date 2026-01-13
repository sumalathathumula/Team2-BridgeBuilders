#@admin-login
#Feature: Admin Login Controller - Token Generation
#
#  Background:
#    Given Admin sets No Auth
#
#  @Include
#  Scenario: Check if admin able to generate token with valid credential
#    Given  Admin creates request with valid credentials
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 200 created with auto generated token
#
#  @Working
#  Scenario: Check if admin able to generate token with invalid method
#    Given  Admin creates request with valid credentials
#    When Admin call GET HTTPS method with post endpoint
#    Then Admin receives 405
#
#  @Working
#  Scenario: Check if admin able to generate token with invalid base URL
#    Given  Admin creates request with valid credentials
#    When Admin calls Post Https method with invalid base URL
#    Then Admin does not receive any status
#
#  @WorkingR
#  Scenario: Check if admin able to generate token with invalid content type
#    Given  Admin creates request with valid credentials
#    When Admin calls Post Https method with invalid content type
#    Then Admin receives 415
#
#
#  Scenario: Check if admin able to generate token with invalid endpoint
#    Given  Admin creates request with valid credentials
#    When Admin calls Post Https method with invalid endpoint
#    Then Admin does not receive any status
#
#  Scenario: Check if admin able to generate token with special characters in admin email
#    Given Admin creates request with special characters in admin email
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 400
#
#  Scenario: Check if admin able to generate token with special characters in password
#    Given  Admin creates request with special characters in password
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 401
#
#  Scenario: Check if admin able to generate token with number in email
#    Given  Admin creates request with numbers in email
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 400
#
#  Scenario: Check if admin able to generate token with numbers in password
#    Given  Admin creates request with numbers in password
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 400
#
#  Scenario: Check if admin able to generate token with Null password
#    Given  Admin creates request with Null password
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 400
#
#  Scenario: Check if admin able to generate token with Null Email
#    Given  Admin creates request with Null email
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 400
#
#  Scenario: Check if admin able to generate token with Null body
#    Given  Admin creates request with Null body
#    When Admin calls Post Https method with valid endpoint
#    Then Admin receives 400
#
