#@admin-logout
#Feature: Admin Logout
#
#Background:
#Given admin sets authorization to bearer Token with old token
#
#Scenario: Check if admin able to create request for the logout token
#Given  Admin creates request after logout
#When Admin calls Get Https method with valid endpoint
#Then Admin receives 401  unauthorized