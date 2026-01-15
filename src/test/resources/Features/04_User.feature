#@user
Feature: User Module
 
 Background:
    Given Admin sets Authorization to Bearer Token to create user.   

  Scenario Outline: Admin able to create a User with valid endpoint and request body
    Given Admin creates POST Request  with valid data in request body for create user
    When Admin sends HTTPS Request with data from row "<Scenario>" for create user
    Then the response status should be equal to ExpectedStatus for create user

  Examples:
    | Scenario 			    														 |
  	| CreateUserWithValidDataR01										 |
  	|CreateUserWithValidDataR02												|
  	|CreateUserWithValidDataR03												|
  	|	CreateUserWithExistingPhoneNumber							  |
  	|CreateUserWithMandatoryFieldsValidData						|
  	|CreateUserWithInvaliduserFirstName								|
  	|CreateUserWithInvaliduserLastName 								|
  	|CreateUserWithInvaliduserLocation								|
  	|CreateUserWithInvaliduserPhoneNumber							|
  	|CreateUserWithInvaliduserRoleStatus							|
  	|CreateUserWithInvaliduserTimeZone								|
  	|CreateUserWithInvaliduserVisaStatus 							|
  	|CreateUserWithInvaliduserLoginEmail							|
  	|CreateUserWithSpecialCharacterInuserLoginEmail		|
  	|CreateUserWithSpecialCharacterInuserVisaStatus		|
  	|CreateUserWithSpecialCharacterInuserTimeZone			|
  	|CreateUserWithSpecialCharacterInuserRoleStatus	  |
  	|CreateUserWithSpecialCharacterInroleId						|
  	|CreateUserWithSpecialCharacterInuserPhoneNumber 	|
  	|CreateUserWithSpecialCharacterInuserMiddleName		|
  	|CreateUserWithSpecialCharacterInuserVisaStatus		|
  	|CreateUserWithSpecialCharacterInuserLocation			|
  	|CreateUserWithSpecialCharacterInuserLinkedinUrl 	|
  	|CreateUserWithSpecialCharacterInuserLastName 		|
  	|CreateUserWithSpecialCharacterInuserFirstName		|
  	|CreateUserWithSpecialCharacterWithNumericloginStatus|
  	|CreateUserWithEmptyuserFirstName								|
  	|CreateUserWithEmptyuserLastName								|
  	|CreateUserWithEmptyuserLoginEmail							|
  	|CreateUserWithEmptyuserPhoneNumber							|
  	|CreateUserWithNoAuth														|
  	|CreateUserWithInvalidEndpoint									|
  	
  	    	
  	
  	  	#NOAUTH
  	Scenario Outline: Check if admin able to create a user with valid endpoint and request body without authorization.
    Given Admin sets Authorization to No Auth, creates POST Request with valid data in request body for create user
    When Admin sends HTTPS Request with data from row "<Scenario>" for create user
    Then the response status should be equal to ExpectedStatus for create user
    
    Examples:
    | Scenario 			    														 |
  	| CreateUserWithNoAuth													 |
  	
    #INVALIDENDPOINT
  Scenario Outline: Check if admin able to create a user with invalid endpoint
    Given Admin creates POST Request  with valid data in request body with invalid endpoint for create user
    When Admin sends HTTPS Request with data from row "<Scenario>" for create user
    Then the response status should be equal to ExpectedStatus for create user
    
    Examples:
    | Scenario 			    														 |
  	| CreateUserWithInvalidEndpoint                  |
  	
  	#GETALLUSERS
    Scenario: Check if admin able to retrieve all admin  with valid LMS API    
    Given Admin creates GET Request for the LMS API endpoint 
    When Admin sends HTTPS Request with endpoint for get all users 
    Then Admin receives 200 OK Status with response body for get all users.
    
    Scenario: Check if admin able to retrieve all users with invalid Endpoint
    Given Admin creates GET Request with invalid endpoint for all users 
    When Admin sends HTTPS Request with  invalid endpoint for all users 
    Then Admin receives 404 status with error message Not Found for get all users
    
    #GETUSERBYUSERID
    @getuserbyuserid
    Scenario: Check if admin able to retrieve a admin with valid admin ID    
    Given Admin creates GET Request for the LMS API endpoint with valid admin ID
    When  Admin sends HTTPS Request with endpoint to getuserbyuserid  details
    Then Admin receives 200 OK Status with response body for getuserbyuserid.  
    
     #GETUSERSBYROLEID 
      @getusersbyroleid
      Scenario: Check if admin able to retrieve a admin with valid role ID    
    Given Admin creates GET Request for the LMS API endpoint with valid role ID
    When  Admin sends HTTPS Request with endpoint to getusersbyroleid  details
    Then Admin receives 200 OK Status with response body for getusersbyroleid. 
    
    #PUTREQUEST(Assign admin to Program / Batch by admin ID)
    @UPDATE_USERROLE_PROGRAM_BATCH_STATUS
    Scenario Outline: Admin able to assign admin to program or batch using PUT API
    Given Admin creates PUT Request with valid LMS endpoint to assign admin
    When Admin sends HTTPS Request with data from row "<Scenario>" for assign admin
    Then the response status should be equal to ExpectedStatus for assign admin

    Examples:
      | Scenario                                                           |
      | AssignAdminToProgramBatchWithValidData                            |
      | AssignAdminToProgramBatchWithInvalidAdminId                        |
      | AssignAdminToProgramBatchWithMissingMandatoryField                 |
      
     #PUTREQUEST(Update admin role status by admin ID)
     @UPDATE_USER_ROLEID 
    Scenario Outline: Admin able to update admin role status using PUT API    
    Given Admin creates PUT Request with valid LMS endpoint to update admin role status
    When Admin sends HTTPS Request with data from row "<Scenario>" for update admin role status
    Then the response status should be equal to ExpectedStatus for update admin role status

    Examples:
      | Scenario                                                  |
      | UpdateAdminRoleStatusWithValidAdminId                     |
      | UpdateAdminRoleStatusWithInvalidAdminId                   |
      | UpdateAdminRoleStatusWithMissingMandatoryField            |
      
      
      
      
    
              
 