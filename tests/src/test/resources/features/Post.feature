@post
Feature: Post

  Scenario: Activate package for subscriber
    When Send post request to "subscriber/1/packs/activate" with parameters
      | packageId:1                 |
      | activationDate:13.02.2017   |
      | deactivationDate:14.02.2017 |

    Then Response id > 100

  Scenario: Deactivate package for subscriber
    When Send post request to "subscriber/1/packs/deactivate" with parameters
      | packageId:1                 |
      | deactivationDate:14.02.2017 |

    Then Response id > 100

  Scenario: Provising
    When Send post request to "provision/packs" with parameters
      | subscriberId:2              |
      | packageId:2                 |
      | actionType:DEACTIVATE       |
      | activationDate:12.02.2017   |
      | deactivationDate:15.02.2017 |
      | traceNumber:100             |

    Then Response id > 100