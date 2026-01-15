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
      |      Scenario                                         |

      |      Create Skill with Valid Data                     |

      |      Create Skill with Already existing SkillName     |

      |      Create Skill with Missing SkillName field        |
