@get
Feature: Get

  Scenario: product offering by id
    When Send get request to url "/poffering/1"
    Then Response contains
      | id:1                      |
      | code:code1                |
      | name:1st product offering |

  Scenario: active product offerings
    When Send get request to url "/poffering/active"
    Then Response contains
      | id:   |
      | code: |
      | name: |

  Scenario: product offering by name
    When Send get request to url "/poffering?name=1st"
    Then Response contains
      | id:1                      |
      | code:code1                |
      | name:1st product offering |


  Scenario: product offering by name
    When Send get request to url "/poffering/avail/30"
    Then Response contains
      | sidRegion:Chelyabinskaya region |
      | sidBranch:TSCHF                 |
      | salesChannel:USSD               |
      | availabilityAction:1            |
      | dateRange:                      |
      | dateFrom:12.01.2017 06:47       |
      | dateTo:12.11.2017 06:47         |
      | productOffering:                |
      | id:2                            |
      | code:code2                      |
      | name:2nd product offering       |
      | validFor:                       |
      | dateFrom:12.01.2017 06:47       |
      | dateTo:12.11.2017 06:47         |