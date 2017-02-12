@get
Feature: Get

  Scenario: pack list
    When Send get request to url "packs"
    Then Response contains
      | packid:1        |
      | name:Internet_L |
      | packid:2        |
      | name:Internet_S |
      | packid:3        |
      | name:SMS        |

  Scenario: packs list of subscriber
    When Send get request to url "subscriber/1/packs"
    Then Response contains
      | name:Internet_L |
      | ActionType:1 |
      | name:SMS |
      | ActionType:1 |
      | name:Internet_S |
      | ActionType:2 |

  Scenario: packs list with limit and offset
    When Send get request to url "packs" limit = 2 offset = 1
    Then Response contains
      | packid:2        |
      | name:Internet_S |
      | packid:3        |
      | name:SMS        |