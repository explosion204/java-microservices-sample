Feature: download binary resource
  Scenario Outline: download file
    Given file <filename> is successfully uploaded
    When user tries to download file with id = <id>
    Then user receives status code <status_code> for download request
    And response has a Content-Type header = <content_type>

    Examples:
      | id | filename            | status_code | content_type       |
      | 1  | test_data/valid.mp3 | 200         | audio/mpeg         |
      | 2  | test_data/valid.mp3 | 404         | application/json   |