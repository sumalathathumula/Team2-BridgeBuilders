#@Forgot-password
#Feature: Forgot Password Controller
#
#  Background:
#    Given Admin sets No Auth for forgot-password
#
#  Scenario: Check if admin able to generate token with valid credential
#    Given  Admin creates request for forgot-password with valid credentials
#    When Admin calls Post Https method for forgot-password with valid endpoint
#    Then Admin receives 201 created with auto generated token for forgot-password
#
#  Scenario: Check if admin able to generate token with invalid endpoint
#    Given  Admin creates request for forgot-password with valid credentials
#    When Admin calls Post Https method for forgot-password with invalid endpoint
#    Then Admin does not receive any status for forgot-password
#
#  Scenario: Check if admin able to generate token with special characters in admin email
#    Given  Admin creates request for forgot-password with special characters in admin email
#    When Admin calls Post Https method for forgot-password with valid endpoint
#    Then Admin receives 400 with message invalid email for forgot-password
#
#
#  Scenario: Check if admin able to generate token with invalid admin email
#    Given  Admin creates request for forgot-password with invalid admin email
#    When Admin calls Post Https method for forgot-password with valid endpoint
#    Then Admin receives 400 with message invalid email for forgot-password
#
#  Scenario: Check if admin able to generate token with Null body
#    Given  Admin creates request for forgot-password with Null body
#    When Admin calls Post Https method for forgot-password with valid endpoint
#    Then Admin receives 400 with message "Email Id is Mandatory" and false success message for forgot-password
#
#  Scenario: Check if admin able to generate token with invalid content type
#    Given  Admin creates request for forgot-password with valid credentials
#    When Admin calls Post Https method for forgot-password with invalid content type
#    Then Admin receives 415 unsupported media type for forgot-password
