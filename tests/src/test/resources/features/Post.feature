@post
Feature: Post

  @ping
  Scenario: New product offering
    When Send post request to "poffering/new" with parameters
      | code:lalala                   |
      | name:gg123                    |
      | description:Opisanie         |
      | dateStart:27-07-2017 09:53:33 |
      | dateEnd":27-09-2017 09:53:33  |
    Then Response contains
      | id:                   |
      | code:lalala           |
      | name":gg123           |
      | description":Opisanie |

  Scenario: Product offering avil
    When Send post request to "poffering/avail/new" with parameters
      | regionId:15               |
      | branchId:  7              |
      | salesChannel:26           |
      | availabilityAction:1      |
      | Zone:18                   |
      | dateFrom:12.01.2017 06:47 |
      | dateTo:12.11.2017 06:47   |
      | productOfferingId:2       |
    Then Response contains
      | sidRegion:Chelyabinskaya region |
      | sidBranch:TSCHF                 |
      | salesChannel:USSD               |
      | availabilityAction:1            |
      | productOffering:                |
      | id:2                            |
      | code:code2                      |
      | name:2nd product offering       |
