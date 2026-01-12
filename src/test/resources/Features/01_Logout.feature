#@admin-logout
#Feature: Admin Logout
#
#  Background:
#    Given Admin sets authorization to bearer Token with token
#
#  Scenario: Check if admin able to logout
#    Given  Admin creates request
#    When Admin calls Get Https method with valid endpoint
#    Then Admin receives 200 ok  and response with "Logout successful"
#
#  Scenario: Check if admin able to logout  with invalid baseURL
#    Given  Admin creates request
#    When Admin calls Get Https mehod with invalid baseURL
#    Then Admin receives 404 Not found
#
#  Scenario: Check if admin able to logout  with invalid endpoint
#    Given  Admin creates request
#    When Admin calls Get Https method withinvalid endpoint
#    Then Admin receives 404 Not found
#
#  Scenario: Check if admin able to logout  with invalid method
#    Given  Admin creates request
#    When Admin calls POST Https method with invalid endpoint
#    Then Admin receives 405 method not allowed
#
#  Scenario: Check if admin able to logout  with invalid method
#    Given  Admin creates request
#    When Admin calls POST Https method with invalid endpoint
#    Then Admin receives 405 method not allowed
#
#
#
#
#
#
#
#
