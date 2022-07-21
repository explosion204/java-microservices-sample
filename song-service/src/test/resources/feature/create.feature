Feature: create song metadata
  Scenario Outline: create song metadata
    When user sends request with body: <resource_id>, <name>, <artist>, <album>, <length>, <year>
    Then user receives status code <status_code> for create request

    Examples:
      | resource_id | name      | artist      | album      | length | year | status_code |
      | 1           | test_name | test_artist | test_album | 1:00   | 2022 | 200         |
      | 1           | null      | test_artist | test_album | 1:00   | 2022 | 400         |
