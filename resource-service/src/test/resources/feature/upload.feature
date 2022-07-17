Feature: upload binary resource
  Scenario: upload valid file
    When user uploads file test_data/valid.mp3
    Then user receives status code 200 for upload request
    And resource id = 1 was sent to queue

  Scenario: upload invalid file
    When user uploads file test_data/invalid.mp3
    Then user receives status code 400 for upload request