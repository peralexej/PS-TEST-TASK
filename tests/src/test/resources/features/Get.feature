@get
Feature: Get

  Scenario: pack list
    When Send get request to url "packs"
    Then Response contains
      | packId:1        |
      | name:Internet_L |
      | packId:2        |
      | name:Internet_S |
      | packId:3        |
      | name:SMS        |

  Scenario: packs list of subscriber
    When Send get request to url "subscriber/1/packs"
    Then Response contains
      | packId:1              |
      | name:Internet_L       |
      | actionType:ACTIVATE   |
      | packId:2              |
      | name:Internet_S       |
      | actionType:DEACTIVATE |
      | packId:3              |
      | name:SMS              |
      | actionType:ACTIVATE   |

  Scenario: packs list with limit and offset
    When Send get request to url "packs" limit = 2 offset = 1
    Then Response contains
      | packId:2        |
      | name:Internet_S |
      | packId:3        |
      | name:SMS        |