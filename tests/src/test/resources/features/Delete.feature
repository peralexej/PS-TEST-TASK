@delete
Feature: delete

  Scenario: product offering by id
    When Send delete request to url "poffering/avail/delete/31"
    When Send get request to url "poffering/avail/31"
    Then Response is null