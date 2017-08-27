@post
Feature: Put

  Scenario: Product offering avil
    When Send put request to "poffering/avail/update" with parameters
      | id:30                     |
      | regionId:14               |
      | branchId:  8              |
      | salesChannel:26           |
      | availabilityAction:1      |
      | Zone:18                   |
      | dateFrom:12.01.2017 06:47 |
      | dateTo:12.11.2017 06:47   |
      | productOfferingId:2       |
      | typeOfRule:1              |

    When Send get request to url "/poffering/avail/30"
    Then Response contains
      | sidRegion:Sverdlovskaya region |
      | sidBranch:UF                   |
      | salesChannel:USSD              |
      | availabilityAction:1           |
      | dateRange:                     |
      | dateFrom:12.01.2017 06:47      |
      | dateTo:12.11.2017 06:47        |
      | productOffering:               |
      | id:2                           |
      | code:code2                     |
      | name:2nd product offering      |
      | validFor:                      |
      | dateFrom:12.01.2017 06:47      |
      | dateTo:12.11.2017 06:47        |

    When Send put request to "poffering/avail/update" with parameters
      | id:30                     |
      | regionId:15               |
      | branchId:  7              |
      | salesChannel:26           |
      | availabilityAction:1      |
      | Zone:18                   |
      | dateFrom:12.01.2017 06:47 |
      | dateTo:12.11.2017 06:47   |
      | productOfferingId:2       |
      | typeOfRule:1              |
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