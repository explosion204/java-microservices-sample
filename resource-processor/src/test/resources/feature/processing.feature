Feature: binary processing
  Scenario Outline: process binary file
    Given file <filename> is loaded with expected metadata: <resource_id>, <name>, <artist>, <album>, <length>, <year>
    When file id = 1 is sent to input queue
    Then file id = <resource_id> is requested from resource-service
    And file id = <resource_id> is deleted from STAGING storage and uploaded to a PERMANENT one with new id
    And metadata with new (incremented) <resource_id>, <name>, <artist>, <album>, <length>, <year> is sent to song-service

    Examples:
      | filename             | resource_id | name                                    | artist               | album                                         | length | year |
      | test_data/sample.mp3 | 1           | A Stranger I Remain (Maniac Agenda Mix) | Jamie Christopherson | Metal Gear Rising: Revengeance (Vocal Tracks) | 2:25   | 2013 |

