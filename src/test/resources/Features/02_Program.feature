		@Program
		Feature:Program Module API 
		
		Background: 
		Given Admin has valid authorization to create Program Id
		
		@POST
		Scenario Outline: Check if Admin able to create a program with different scenarios
		Given Admin creates POST Request with different scenario
		When Admin sends HTTPS Request "<Scenario>" with endpoint
		Then Admin receives status code with response body
		
		Examples:
		|Scenario					 |
		|Valid request bodyFB		|
		|Valid request bodyFDPID	|
		|Valid request bodyFDPN	|
		|Invalid request body |
		|Missing request body |
		
		@GETALLPROGRAM
		Scenario:
		Check if Admin able to retrieve all programs with Valid Endpoint
		Given Admin creates GET Request for the LMS API
		When Admin sends HTTPS Request with Valid Endpoint
		Then Admin receives 200 with OK message
		@GETALLPROGRAMINVALID
		Scenario:
		Check if Admin able to retrieve all programs with InValid Endpoint
		Given Admin creates GET Request for LMS API
		When Admin sends HTTPS Request with InValid Endpoint
		Then Admin receives 404 with Not found message
		@GETPROGRAMBYPROGRAMID
		Scenario:
		Check if Admin able to retrieve all programs by programId
		Given Admin creates GET Request with  valid programId for the LMS API
		When Admin sends HTTPS Request with with valid endpoint
		Then Admin receives 200 with OK message
		@GETPROGRAMBYPROGRAMID1
		Scenario:
		Check if Admin able to retrieve all programs by programId for invalid endpoint
		Given Admin creates GET Request with  Invalid programId for the LMS API
		When Admin sends HTTPS Request with with Invalid endpoint
		Then Admin receives 404 with Not found message
		@GETALLPROGRAMWITHUSER
		Scenario:
		Check if Admin able to retrieve all programs with valid Endpoint
		Given Admin creates GET Request to get all programs with user details
		When Admin sends Request with valid endpoint
		Then Admin receives 200 OK with response body
		@GETALLPROGRAMWITHUSER1
		Scenario:
		Check if Admin able to retrieve all programs with Invalid Endpoint
		Given Admin creates GET Request to get all program user details
		When Admin sends Request with Invalid endpoint
		Then Admin receives 404 with Not found message
		
		@UPDATEPROGRAMBYPROGRAMID
		Scenario Outline:
		Check if Admin able to update a program with different request body conditions
		Given Admin updates PUT Request with valid Endpoint
		When Admin sends HTTPS "<Scenario>" RequestBody
		Then Admin receives status code and message
		Examples:
		|Scenario			   |
		|ValidMandatoryField   |
		|MissingMandatoryField |
		|InvalidMandatoryField |
		
		@UPDATEPROGRAMBYPROGRAMNAME
		Scenario Outline:
		Check if Admin able to update a program by program name
		Given Admin updates PUT Request for program by program name
		When Admin sends HTTPS "<Scenario>" RequestBody for update
		Then Admin receives status code with status message
		Examples:
		|Scenario		|
		|ValidRequest   |
		|MissingRequest |
		|InvalidRequest |

	
