 
Feature: Create Batch API

  Background:
    Given Admin sets Authorization to Bearer Token. 

  # ============================
  # POST Request
  # ============================  
      

  Scenario Outline: Admin able to create a Batch with valid endpoint and request body
    Given Admin creates POST Request with valid data in request body for create batch
    When Admin sends HTTPS Request with data from row "<Scenario>" for create batch
    Then the response status should be equal to ExpectedStatus for create batch

  Examples:
  
    | Scenario 	                                     |
    | CreateBatchWithValidBatchName		    					 |
    | CreateBatchWithInvalidBatchName	    					 |
  	| CreateBatchWithValidData											 |
  	| CreateBatchWithMissingAdditionalFields         |
  	| CreateBatchWithValidRequestBody                |
  	| CreateBatchWithEmptyProgramId                  |
  	| CreateBatchWithEmptyBatchStatus                |
  	| CreateBatchWithEmptyNoOfClasses                |
  	| CreateBatchWithEmptyBatchName                  |
  	| CreateBatchWithValidEndpointNonexistingBody    |
  	| CreateBatchWithMissingMandatoryFields          |
  	| CreateBatchWithInactiveProgramId               |
  	| CreateBatchWithInvalidData                     |
  	| CreateBatchWithValidBatchDescription           |
  
  @admin-login	
  Scenario Outline: Check if admin is able to create a Batch with negative scenarios
  Given Admin sets Authorization to "<Authorization>"
  When Admin sends HTTPS POST request to "<Endpoint>"
  Then Admin should receive "<ExpectedStatusCode>" response

Examples:
| Authorization  | Endpoint       | ExpectedStatusCode |
| NoAuth         | valid          | 401 |
| BearerToken    | Invalid        | 404 |
  
  	

  # ============================
  # Retrieve All Batches
  # ============================  
      

  Scenario Outline: Admin retrieves all batches with GET API
    Given Admin prepares GET request with "<scenario>"
    When Admin sends GET request to batches endpoint
    Then Admin should receive <statusCode> with "<message>"

    Examples:
      | scenario                | statusCode | message        |
      | valid                   | 200        | OK             |
      | searchValid             | 200        | OK             |
      | noAuth                  | 401        | Unauthorized   |
      | validwithInvalidEndpoint| 404        | Not Found      |
  
  
    
  # ============================
  # Retrieve batch by Batch ID
  # ============================  
      
	   Scenario Outline: check if admin retrieves all batches with GET API using batchid
	    Given Admin creates  GET request with "<scenario>"
	    When Admin sends GET request to batches endpoint with batchid
	    Then Admin receive <statusCode> with "<message>"
	
	    Examples:
      | scenario                                  | statusCode | message        |
      | noAuth                                    | 401        | Unauthorized   |
      | ValidBatchID                              | 200        | OK             |
      | RetrieveBatchAfterDeleting                | 200        | OK             |
      | RetrieveBatchwithInvalidBatchID           | 404        | Not Found      |
      | BatchInvalidEndpoint                      | 404        | Not Found      |
      
  
  # ============================
  # Retrieve batch by Batch NAME
  # ============================
  
   Scenario Outline: Check if admin retrieves batch using Batch Name
    Given Admin creates GET request with batchname and "<scenario>"
    When Admin sends GET request to batches endpoint with batchname
    Then Admin should receive <statusCode> with "<message>"

    Examples:
      | scenario                         | statusCode | message      |
      | noAuth                           | 401        | Unauthorized |
      | ValidBatchName                   | 200        | OK           |
      | InvalidBatchName                 | 404        | Not Found    |
      | RetrieveDeletedBatchByName       | 404        | Not Found    |
    

  # ============================
  # Retrieve batch by Program ID
  # ============================
  

  Scenario Outline: Admin retrieves batch using Program ID
    Given Admin prepares GET request with ProgramID "<scenario>"
    When Admin sends GET request to batches endpoint with Program ID
    Then Admin should receive <statusCode> with "<message>"

    Examples:
      | scenario                           | statusCode | message        |
      | noAuth                             | 401        | Unauthorized   |
      | ValidProgramID                     | 200        | OK             |
      | InvalidProgramID                   | 404        | Not Found      |
      | ProgramInvalidEndpoint             | 404        | Not Found      |
      | RetrieveBatchAfterProgramDeleted   | 404        | Not Found      |
  

  # ============================
  # Delete batch by Batch ID
  # ============================
      
      
  Scenario Outline: Check if admin able to delete a Batch by BatchID
  Given Admin sets base URI and prepares DELETE request for "<ScenarioType>"
  When Admin sends DELETE request
  Then the response status should be "<ExpectedStatusCode>"
      
   Examples:
    | ScenarioType           | ExpectedStatusCode |
    | ValidBatchId           | 200                |
    | InvalidEndpoint        | 404                |
    | InvalidBatchId         | 404                |
    | WithoutAuthorization   | 401                |
    
    
  # ============================
  # Update batch by Batch ID
  # ============================
      
    Scenario Outline: Admin able to update a Batch with valid endpoint and request body
    Given Admin creates UPDATE Request with valid data in request body for update batch
    When Admin sends HTTPS Request with data from row "<Scenario>" for update batch
    Then the response status should be equal to ExpectedStatus for update batch

  Examples:
  
    | Scenario 	                                     |
    | UpdateBatchIdWithAllFields                     |
  	| UpdateBatchIdWithMandatoryFields               |
  	| UpdateBatchIdWithMandatoryFields               |
    | UpdateBatchWithInvalidData                     |
  	| UpdateBatchWithDeletedBatchId                  |
  	| UpdateBatchWithDeletedProgramID                |
  	| UpdateBatchWithInvalidBatchId                  |
  	
      