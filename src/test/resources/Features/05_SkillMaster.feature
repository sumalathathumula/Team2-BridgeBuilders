@Skill
Feature: Skill Master Module API
  Background:
    Given Admin sets Authorization to Bearer Token to create skill


  @CREATENEWSKILL
  Scenario Outline: check if admin able to create a New Skill with valid endpoint and request body
    Given Admin creates request with valid data in request body for create skill
    When Admin sends HTTPS Request with data from row "<Scenario>"
    Then the response body should be equal to Expected Status message.

    Examples:
      |Scenario                                         |

      |Create Skill with Valid Data                 |

      |Create Skill with Already existing SkillName     |

      |Create Skill with Missing SkillName field        |
      |Create Skill with valid data and Invalid endpoint|

@Getall
  @GETALLSKILLMASTER
  Scenario: Check if Admin able to retrieve all skill master with Valid Endpoint
    Given Admin creates GET Request for the LMS API
    When Admin sends HTTPS Request with Valid Endpoint
    Then Admin receives 200 with OK message
@Getallinvalid
  @GETALLSKILLMASTERINVALID
  Scenario: Check if Admin able to Get all skill master with the InValid Endpoint
    Given Admin creates GET Request for  LMS API
    When Admin sends HTTPS Request with InValid Endpoint
    Then Admin receives 404 with Not Found message

@Getskillmaster
  @GETSKILLBYSKILLMASTERNAME
  Scenario: Check if admin able to get Skill Master Name with Valid Endpoint and skill master name
    Given Admin creates GET Request for the LMS API endpoint with valid skill master name
    When Admin sends HTTPS Request with valid endpoint as skill master name
    Then Admin receives 200 Status with OK message
@Getskillmasterinvalid
  @GETSKILLBYSKILLMASTERNAMEINVALID
    Scenario: Check if admin able to get Skill Master Name with InValid endpoint
    Given Admin creates GET Request for  LMS API with invalid skill master name
    When Admin sends HTTPS Request with InValid SkillMasterName
    Then Admin receives  the 404 with Not found  message



#
#
#  @UPDATESKILLBYSKILLID
#  Scenario Outline: Check if admin able to update Skill Master using PUT request
#    Given Admin creates PUT Request for the LMS API endpoint
#    When   Admin sends HTTPS PUT Request from row "<Scenario>"
#    Then the response body should be equal to "<ExpectedStatusMessage>" and "<ExpectedStatusCode>"
#
#    Examples:
#      | Scenario                            | ExpectedStatusCode | ExpectedStatusMessage |
#      | Update Skill with valid skillId     | 200                | OK                    |
#      | Update Skill with Invalid skillId   | 404                | Not Found             |
#      | Update Skill with empty request body| 400                | Bad Request           |
#      | Update Skill with Invalid endpoint  | 404                | Not Found             |
@Delete
  @DELETESKILLBYSKILLID
   Scenario Outline: Check if admin able to Delete  Skill ID  using DELETE request
    Given Admin creates  DELETE Request for the LMS API endpoint
    When Admin sends HTTPS Request from row "<Scenario>"
    Then the response body should be equal to "<ExpectedStatusMessage>" and "<ExpectedStatusCode>".
    Examples:
      | Scenario                          | ExpectedStatusCode | ExpectedStatusMessage |
      | Delete Skill with valid skillId   | 200               | OK                     |
      | Delete Skill with Invalid skillId | 404               | Not Found              |
      | Delete Skill with Invalid endpoint| 404               | Not Found              |







