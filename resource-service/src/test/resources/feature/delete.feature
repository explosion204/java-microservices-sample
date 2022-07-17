Feature: download binary resource
  Scenario: delete files
    Given file test_data/valid.mp3 is successfully uploaded
    When user tries to delete file with ids = 1,2,3
    Then user receives status code 200 for delete request
    And response contains the ids of deleted items: 1