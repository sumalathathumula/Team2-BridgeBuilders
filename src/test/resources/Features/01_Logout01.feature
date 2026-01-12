#@admin-logout
#Feature: Admin Logout
#
#  Background:
#    Given Admin sets No Auth
#
#  Scenario: Check if admin able to logout
#    Given  Admin creates request
#    When Admin calls Get Https method with valid endpoint
#    Then Admin receives 401  unauthorized