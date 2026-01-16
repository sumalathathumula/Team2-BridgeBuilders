Feature: Skill Master Module API
  Background:
    Given Admin sets Authorization to Bearer Token to create skill


  @CREATENEWSKILL
  Scenario Outline: check if admin able to create a New Skill with valid endpoint and request body
    Given Admin creates request with valid data in request body for create skill
    When Admin sends HTTPS Request with data from row "<Scenario>"
    Then the response body should be equal to Expected Status message.

    Examples:
      |  Scenario                                         |

      |      Create Skill with Valid Data                 |

      |  Create Skill with Already existing SkillName     |

      |  Create Skill with Missing SkillName field        |

  @GETALLSKILLMASTER
  Scenario Outline: Check if admin able to get all  Skill Master

    Given Admin creates GET Request for the LMS API endpoint

    When Admin sends HTTPS Request from row "<Scenario>"

    Then the response body should be equal to "<ExpectedStatusMessage>" and "<ExpectedStatusCode>"


    Examples:

     |    Scenario                              | ExpectedStatusCode | ExpectedStatusMessage |
     |   Get all Skills by Valid endpoint       | 200                |  OK                   |

     |   Get all Skills by Invalid endpoint     | 404                |  Not Found            |

  @GETSKILLBYSKILLNAME
  Scenario Outline: Check if admin able to get skill by Skill Name
    Given Admin creates GET Request for the LMS API endpoint
    When Admin sends HTTPS Request from row "<Scenario>"
    Then the response body should be equal to "<ExpectedStatusMessage>" and "<ExpectedStatusCode>"

    Examples:
      | Scenario                         | ExpectedStatusCode | ExpectedStatusMessage   |
      | Get Skill Name by Valid endpoint  | 200               | OK                      |
      | Get Skill Name by Invalid endpoint| 404               | Not Found               |
  @UPDATESKILLBYSKILLID
  Scenario Outline: Check if admin able to update Skill Master using PUT request
    Given Admin creates PUT Request for the LMS API endpoint
    When   Admin sends HTTPS PUT Request from row "<Scenario>"
    Then the response body should be equal to "<ExpectedStatusMessage>" and "<ExpectedStatusCode>"

    Examples:
      | Scenario                            | ExpectedStatusCode | ExpectedStatusMessage |
      | Update Skill with valid skillId     | 200                | OK                    |
      | Update Skill with Invalid skillId   | 404                | Not Found             |
      | Update Skill with empty request body| 400                | Bad Request           |
      | Update Skill with Invalid endpoint  | 404                | Not Found             |

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







