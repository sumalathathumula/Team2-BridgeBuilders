#@Program
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
		
