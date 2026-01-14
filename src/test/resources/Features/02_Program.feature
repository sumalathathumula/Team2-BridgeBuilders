Feature:Program Module API 

Background: 
Given Admin has valid authorization to create Program Id

@POST
Scenario Outline:
Check if Admin able to create a program with different scenarios
Given Admin creates POST Request with different scenario
When Admin sends HTTPS Request "<Scenario>" with endpoint
Then Admin receives status code with response body

Examples:
|Scenario					|
|Valid request body			|
|Invalid request body		|
|Missing request body		|



@GETALLPROGRAM
Scenario:
Check if Admin able to retrieve all programs with Valid Endpoint
Given Admin creates GET Request for the LMS API
When Admin sends HTTPS Request with Valid Endpoint
Then Admin receives 200 with OK message 

@GETALLPROGRAM1
Scenario:
Check if Admin able to retrieve all programs with InValid Endpoint
Given Admin creates GET Request for LMS API
When Admin sends HTTPS Request with InValid Endpoint
Then Admin receives 404 with Not found message 


#@GETPROGRAMBYPROGRAMID
#Scenario Outline:
#Check if Admin able to retrieve all programs by programId
#Given Admin creates GET Request with "<programId>" using "<Auth>" for the LMS API
#When Admin sends HTTPS Request with "<Scenario>"
#Then Admin receives "<status code>" with "<status message>" 

#Examples:
#|programId		|	Auth  	|	Scenario			|	status code		|	status message		|
#|Valid			|	Valid  	|	Valid Endpoint		|	200				|	OK					|
#|Invalid		|	Valid	|	Valid Endpoint		|	404				|	Not Found			|
#|Valid			|	Valid	|	Inavlid baseUR		|	404				|	Not Found			|
#|Valid			|	Invalid	|	Valid Endpoint		| 	401				|	Unauthorized		|
#|Valid			|	Valid	|	Invalid Endpoint	|	404				|	Not found			|

#@GETALLPROGRAMWITHUSER
#Scenario Outline:
#Check if Admin able to retrieve all programs with different request conditions
#Given Admin creates GET Request with "<Auth>" for API
#When Admin sends Request with "<scenario>"
#Then Admin receives "<status code>" with "<status message>" 

#Examples:
#|Auth	|	scenario		|	status code		| status message	|
#|Valid	|	Valid Endpoint	|	200				| OK				|
#|Valid	|	Invalid Endpoint|	404				| Not Found			|
#|Valid	|	Invalid Method	|	405				| Method Not Allowed|
#|Invalid|	Valid Endpoint	|	401				| Unauthorized		|

#@UPDATEPROGRAMBYPROGRAMID
#Scenario Outline:
#Check if Admin able to update a program with different request body conditions
#Given Admin updates PUT Request with "<Auth>" 
#When Admin sends HTTPS "<RequestBody>" with "<Endpoint>"
#Then Admin receives "<status code>" with "<status message>"

#Examples:
#|Auth	|	RequestBody		|	Endpoint		|	status code		| status message	|
#|Valid	|	Valid 			|	Valid Endpoint	|	200				| OK				|
#|Valid	|	InValid			|	Invalid Endpoint|	400				| Bad Request		|
#|Valid	|	Valid			|	Invalid Endpoint|	404				| Not Found			|
#|Valid	|	Valid			|	Invalid Method	|	405				| Method Not Allowed|
#|Invalid|	Valid			|	Valid Endpoint	|	401				| Unauthorized		|


#@UPDATEPROGRAMBYPROGRAMNAME
#Scenario Outline:
#Check if Admin able to update a program with different request body conditions
#Given Admin updates PUT Request with "<Auth>" 
#When Admin sends HTTPS "<RequestBody>" with "<Endpoint>"
#Then Admin receives "<status code>" with "<status message>"

#Examples:
#|Auth	|	RequestBody		|	Endpoint		|	status code		| status message	|
#|Valid	|	Valid 			|	Valid Endpoint	|	200				| OK				|
#|Valid	|	InValid			|	Invalid Endpoint|	400				| Bad Request		|
#|Valid	|	InValid			|	valid Endpoint	|	404				| Not Found			|
#|Invalid|	Valid			|	Valid Endpoint	|	401				| Unauthorized		|

#@DELETEPROGRAMBYPROGRAMID
#Scenario Outline:
#Check if Admin able to delete a program by program id with valid authorization
#Given Admin creates DELETE Request with "<programID>"
#When Admin sends HTTPS Request with endpoint
#Then Admin receives "<statuscode"> with "<statusmessage>"

#Examples:
#|programID		|	statuscode		|	statusmessage	|
#|Valid			|	200				|	OK				|
#|Invalid		|	404				|	Not found		|

#@DELETEPROGRAMBYPROGRAMNAME
#Scenario Outline:
#Check if Admin able to delete a program by program id with valid authorization
#Given Admin creates DELETE Request with "<programName>"
#When Admin sends HTTPS Request with endpoint
#Then Admin receives "<statuscode"> with "<statusmessage>"

#Examples:
#|programName	|	statuscode		|	statusmessage	|
#|Valid			|	200				|	OK				|
#|Invalid		|	404				|	Not found		|

