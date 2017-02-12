@post
Feature: Post

  Scenario: Activate package for subscriber
    When Send post request to "subscriber/2/packs/activate" with parameters
      | packId:2 |
    Then Response contains
      | id: |
      | conflict:1 |
  Scenario: Deactivate package for subscriber
    When Send post request to "subscriber/2/packs/deactivate" with parameters
      | packId:2 |
    Then Response contains
      | id: |
      | conflict:1 |

  Scenario: Provising
    When Send post request to "provision/packs" with parameters
      | subscriberId:2 |
      | packageId:2 |
      | actionType:DEACTIVATE |
      | activationDate:12.02.2017 |
      | deactivationDate:15.02.2017 |
      | traceNumber:100 |

    Then Response contains
      | id:1 |