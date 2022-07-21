Feature: E2E Testing
  Scenario: E2E Functioning
    Given services are up and running
    When I upload sample MP3 file to resource-service
    Then the file is successfully loaded to storage
    And the song metadata is successfully parsed and stored in song-db