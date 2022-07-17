Feature: delete song metadata
  Scenario: delete song metadata by id
    Given metadata exists: 1, test_name, test_artist, test_album, 1:00, 2022
    When user tries to delete metadata with ids = 1,2,3
    Then user receives status code 200 for delete request
    And response contains the ids of deleted items: 1