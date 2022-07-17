Feature: find song metadata
  Scenario Outline: find song metadata by id
    Given metadata exists: <resource_id>, <name>, <artist>, <album>, <length>, <year>
    When user tries to find metadata with id = <id>
    Then user receives status code <status_code> for find request

    Examples:
      | id | resource_id | name      | artist      | album      | length | year | status_code |
      | 1  | 1           | test_name | test_artist | test_album | 1:00   | 2022 | 200         |
      | 2  | 1           | test_name | test_artist | test_album | 1:00   | 2022 | 404         |