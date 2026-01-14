Feature:Program Module API 

Background: Admin creates Program Id for LMS with valid authorization

@POST
Scenario Outline:
Check if Admin able to create a program with different scenarios
Given Admin creates POST Request with different scenario
When Admin sends HTTPS Request "<scenario>" with endpoint
Then Admin receives status code with response body

Examples:
|	scenario					|
|	Valid request body			|
|	Invalid authorization		|
|	Invalid endpoint			|
|	Invalid	request body		|
|	Missing	request body		|


				

#@GETALLPROGRAM
#Scenario Outline:
#Check if Admin able to retrieve all programs with different scenario
#Given Admin creates GET Request with "<Auth>" for the LMS API
#When Admin sends HTTPS Request with "<scenario>"
#Then Admin receives "<status code>" with "<status message>" 

#Examples:
#|Auth  	| scenario  			|	status code		|	status message		|
#|Valid  |Valid Endpoint 		|	200				|	OK					|
#|Valid	|Invalid Endpoint		|	404				|	Not found			|
#|Valid	|Invalid Method			|	405				|	Method Not Allowed	|
#|Invalid|Valid Endpoint		 	| 	401				|	Unauthorized		|

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

