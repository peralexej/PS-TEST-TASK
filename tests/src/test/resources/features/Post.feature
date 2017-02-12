@post
Feature: Post

  Scenario: Activate package for subscriber
    When Send post request to "subscriber/2/packs/deactivate" with parameters
      | packid:2 |
    Then Response contains
      | id: |
      | conflict:1 |
  Scenario: Deactivate package for subscriber
    When Send post request to "subscriber/2/packs/deactivate" with parameters
      | packid:2 |
    Then Response contains
      | id: |
      | conflict:1 |

