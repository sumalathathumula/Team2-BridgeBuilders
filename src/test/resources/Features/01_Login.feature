#@admin-login 
Feature: Admin Login Controller - Token Generation
  
  Background: 
    Given admin sets No Auth

  @positive 
  Scenario: Check if admin able to generate token with valid credential
    Given Admin creates request with valid credentials
    When Admin calls Post HTTPS method with valid endpoint with data from row "<Scenario>"
    Then Admin receives 200 created with auto generated token
    
    Examples:
    | Scenario 			    														 |
  	| ValidCredentials								               |