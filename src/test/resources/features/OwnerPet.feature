Feature: As an Owner, I would like to add a pet so that I may book visits with a vet.

Background:
  Given the following Owner exists
    | firstName  | lastName     | address          | city        | telephone  |
    | Connor     | McDavid      | 123 This Ave.    | Edmonton    | 6149999999 | 

Scenario: Adding Pet succesfully (Normal Flow)
    When Owner adds a Pet
        | name     | birth date     | pet type    |
        |Balthazar | 2020-01-01     | dog         |
    Then Pet is persisted to the database

Scenario: Adding Pet with invalid Name (Error Flow)
    When Owner adds a Pet
        | name     | birth date     | pet type    |
        | [blank]  | 2020-01-01     | dog         |
    Then Pet is not persisted to the database
    And the page will be redisplayed

Scenario: Adding Pet with invalid Birth date (Error Flow)
    When Owner adds a Pet
        | name        | birth date     | pet type    |
        | Balthazar   | [blank]        | dog         |
    Then Pet is not persisted to the database
    And the page will be redisplayed
