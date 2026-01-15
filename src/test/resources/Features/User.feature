Feature:User Module

Background:
    Given Admin sets Authorization to Bearer Token to create user.   
    
     
@Getallusers  
Scenario: Check if admin able to retrieve for all users    
Given Admin creates GET Request for LMS API endpoint with valid admin ID
When  Admin sends HTTPS Request with endpoint to get all users
Then Admin receives 200 OK Status with response body for all users. 

@Getallusers1
Scenario: Check if admin able to retrieve all usersfor invalid endpoint
Given Admin creates GET Request with invalid endpoint for all user
When Admin sends HTTPS Request with  invalid endpoint for all user 
Then Admin receives 404 status with error message Not Found for get all user

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

@Getemailsofalluserswithactivestatus
Scenario: Check if admin able to retrieve email for all active users    
Given Admin creates GET Request for the LMS API with valid admin ID
When  Admin sends HTTPS Request with endpoint to get email of all active users
Then Admin receives 200 OK Status with response body for all active users. 

@@Getemailsofalluserswithactivestatus1
Scenario: Check if admin able to retrieve email all active users with invalid endpoint
Given Admin creates GET Request with invalid endpoint to fetch email of all active users 
When Admin sends HTTPS Request to fetch email with  invalid endpoint for all active users 
Then Admin receives 404 status with error message Not Found for get all active users

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




