@user
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
  #	|CreateUserWithInvaliduserFirstName								|
  #	|CreateUserWithInvaliduserLastName 								|
  #	|CreateUserWithInvaliduserLocation								|
  #	|CreateUserWithInvaliduserPhoneNumber							|
  #	|CreateUserWithInvaliduserRoleStatus							|
  #	|CreateUserWithInvaliduserTimeZone								|
  #	|CreateUserWithInvaliduserVisaStatus 							|
  #	|CreateUserWithInvaliduserLoginEmail							|
  #	|CreateUserWithSpecialCharacterInuserLoginEmail		|
  #	|CreateUserWithSpecialCharacterInuserVisaStatus		|
  #	|CreateUserWithSpecialCharacterInuserTimeZone			|
  #	|CreateUserWithSpecialCharacterInuserRoleStatus	  |
  #	|CreateUserWithSpecialCharacterInroleId						|
  #	|CreateUserWithSpecialCharacterInuserPhoneNumber 	|
  #	|CreateUserWithSpecialCharacterInuserMiddleName		|
  #	|CreateUserWithSpecialCharacterInuserVisaStatus		|
  #	|CreateUserWithSpecialCharacterInuserLocation			|
  #	|CreateUserWithSpecialCharacterInuserLinkedinUrl 	|
  #	|CreateUserWithSpecialCharacterInuserLastName 		|
  #	|CreateUserWithSpecialCharacterInuserFirstName		|
  #	|CreateUserWithSpecialCharacterWithNumericloginStatus|
  #	|CreateUserWithEmptyuserFirstName								|
  #	|CreateUserWithEmptyuserLastName								|
  #	|CreateUserWithEmptyuserLoginEmail							|
  #	|CreateUserWithEmptyuserPhoneNumber							|
  #	|CreateUserWithNoAuth														|
  #	|CreateUserWithInvalidEndpoint									|
  #	
  	    	
  	
  	  	#NOAUTH
  	Scenario Outline: Check if admin able to create a user with valid endpoint and request body without authorization.
    Given Admin sets Authorization to No Auth, creates POST Request with valid data in request body for create user
    When Admin sends HTTPS Request with data from row "<Scenario>" for create user
    Then the response status should be equal to ExpectedStatus for create user
    
    Examples:
    | Scenario 			    														 |
  	| CreateUserWithNoAuth													 |
  	
    #INVALIDENDPOINTPOSTUSER
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
      
      #GETALLACTIVEUSERS
      @Getallactiveusers
			Scenario: Check if admin able to retrieve for all active users    
			Given Admin creates GET Request for API endpoint with valid admin ID
			When  Admin sends HTTPS Request with endpoint to get all active users
			Then Admin receives 200 OK Status with response for all active users. 
			
			@Getallactiveusers1
			Scenario: Check if admin able to retrieve all active usersfor invalid endpoint
			Given Admin creates GET Request with invalid endpoint for all active users 
			When Admin sends HTTPS Request with  invalid endpoint for all active users 
			Then Admin receives 404 status with message Not Found for get all active users
			
			 #Getemailsofalluserswithactivestatus
			@Getemailsofalluserswithactivestatus
			Scenario: Check if admin able to retrieve email for all active users    
			Given Admin creates GET Request for the LMS API with valid admin ID
			When  Admin sends HTTPS Request with endpoint to get email of all active users
			Then Admin receives 200 OK Status with response body for all active users. 
			
			@Getemailsofalluserswithactivestatus1
			Scenario: Check if admin able to retrieve email all active users with invalid endpoint
			Given Admin creates GET Request with invalid endpoint to fetch email of all active users 
			When Admin sends HTTPS Request to fetch email with  invalid endpoint for all active users 
			Then Admin receives 404 status with error message Not Found for get all active users
			
      #Getallroles		
      @Getallroles  
			Scenario: Check if admin able to retrieve  all roles    
			Given Admin creates GET Request to get all roles API endpoint with valid admin ID
			When  Admin sends HTTPS Request with endpoint to get all roles
			Then Admin receives 200 OK Status with response body for all roles. 
			
			@Getallroles1
			Scenario: Check if admin able to retrieve all roles for invalid endpoint
			Given Admin creates GET Request with invalid endpoint 
			When Admin sends HTTPS Request with  invalid endpoint for all roles 
			Then Admin receives 404 status with error message Not Found for get all user roles
			
			#UPDATEUSERADMIN
			@UPDATEUSER
			Scenario Outline: Admin able to update admin using PUT API   
    Given Admin creates PUT Request with valid LMS endpoint to update admin
    When Admin sends HTTPS Request with data from row "<Scenario>" for update admin
    Then the response status should be equal to ExpectedStatus for update admin

    Examples:
      | Scenario                                           |
      | UpdateAdminWithValidAdminIdAndValidData            |
      | UpdateAdminWithInvalidAdminIdAndValidData          |
      | UpdateAdminWithMissingMandatoryFields              |
      
      #GETUSERFORPROGRAM
    @getuserbyprogramId
    Scenario: Check if admin able to retrieve a program for user   
    Given Admin creates GET Request for the LMS API endpoint for getuserbyprogramId
    When  Admin sends HTTPS Request with endpoint to getuserbyprogramId  details
    Then Admin receives 200 OK Status with response body for getuserbyprogramId. 
    
    Scenario: Check if admin able to retrieve a program for user with invalid program id  
    Given Admin creates GET Request for the LMS API endpoint for getuserbyprogramId with invalid program id
    When  Admin sends HTTPS Request with endpoint to getuserbyprogramId  details with invalid program id
    Then Admin receives 404 status with error message Not Found for get all user programs. 
    
    
    
    #GETUSERFORPROGRAMBATCH
    
     @getuserbyProgrambatchId
    Scenario: Check if admin able to retrieve a batch for user   
    Given Admin creates GET Request for the LMS API endpoint for getuserbyProgrambatchId
    When  Admin sends HTTPS Request with endpoint to getuserbyProgrambatchId  details
    Then Admin receives 200 OK Status with response body for getuserbyProgrambatchId. 
    
    Scenario: Check if admin able to retrieve a program for user with invalid program batch id  
    Given Admin creates GET Request for the LMS API endpoint for getuserbyprogramId with invalid program batch id
    When  Admin sends HTTPS Request with endpoint to getuserbyprogramId  details with invalid program batchid
    Then Admin receives 404 status with error message Not Found for get all user program batches
     
     #DELETEUSERID
     @DELETE_USER
      Scenario: Check if admin able to delete admin with valid admin ID
    
    Given Admin creates a DELETE request with a valid adminId.
    When Admin sends  HTTPS request to the endpoint for deleting admin by admin Id
    Then Admin receives a 200 OK status with a message confirming the deletion of the admin by adminId.
    
     Scenario: Check if admin able to delete admin with invalid admin ID
    
    Given Admin creates DELETE Request with invalid adminId
    When Admin sends HTTPS Request  with invalid adminId for deleting a batch by BatchId
    Then Admin receives 404 Not Found with Message and boolean success details for the deletion of the admin by BatchId.
    
    
    Scenario:  Check if admin able to delete a admin without authorization
    
    Given Admin creates DELETE Request  without authorization for the deletion of the admin by adminId
    When Admin sends  HTTPS request to the endpoint for deleting admin by adminId without authorization
    Then Admin receives 401 Unauthorized Status for the deletion of the admin by adminId.
          
      
      
      
    
              
 